package com.eq.doc.configuration;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.eq.doc.domain.Category;
import com.eq.doc.domain.CategoryPost;
import com.eq.doc.domain.Comment;
import com.eq.doc.domain.Like;
import com.eq.doc.domain.Post;
import com.eq.doc.domain.User;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * create time 2025/8/5 21:31
 * 文件说明
 *
 * @author xuejiaming
 */
@Configuration
@Slf4j
public class AppConfiguration {
    private final EasyEntityQuery easyEntityQuery;

    public AppConfiguration(EasyEntityQuery easyEntityQuery) {
        this.easyEntityQuery = easyEntityQuery;
    }

    @PostConstruct
    public void init() {

        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        easyEntityQuery.syncTableByPackage("com.eq.doc.domain");
        boolean any = easyEntityQuery.queryable(User.class)
                .any();
        if(any){
            return;
        }
        // 1. 生成用户数据（5个用户）
        List<User> users = generateUsers(5);

        // 2. 生成分类数据（3个分类）
        List<Category> categories = generateCategories(10);

        // 3. 生成帖子数据（每个用户发2-3篇帖子）
        List<Post> posts = generatePosts(users, categories);

        // 4. 生成分类-帖子关联数据
        List<CategoryPost> categoryPosts1 = generateCategoryPosts(posts, categories);
        List<CategoryPost> categoryPosts2 = generateCategoryPosts(posts, categories);

        // 5. 生成评论数据（每篇帖子2-4条评论）
        List<Comment> comments = generateComments(users, posts);

        // 6. 生成点赞数据（每篇帖子1-5个赞）
        List<Like> likes = generateLikes(users, posts);
        try(Transaction transaction = easyEntityQuery.beginTransaction()){
            easyEntityQuery.insertable(users).executeRows();
            easyEntityQuery.insertable(categories).executeRows();
            easyEntityQuery.insertable(posts).executeRows();
            easyEntityQuery.insertable(categoryPosts1).executeRows();
            easyEntityQuery.insertable(categoryPosts2).executeRows();
            easyEntityQuery.insertable(comments).executeRows();
            easyEntityQuery.insertable(likes).executeRows();
            transaction.commit();
        }

    }// 生成用户数据

    private static List<User> generateUsers(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> {
                    User user = new User();
                    user.setId(UUID.randomUUID().toString());
                    user.setName("用户" + (char) ('A' + i));
                    user.setPhone("188" + String.format("%08d", ThreadLocalRandom.current().nextInt(10000000)));
                    user.setCreateAt(LocalDateTime.now().minusDays(30 - i));
                    return user;
                })
                .collect(Collectors.toList());
    }

    // 生成分类数据
    private static List<Category> generateCategories(int count) {
        List<String> names = List.of("科技", "生活", "娱乐", "体育", "教育");
        return IntStream.range(0, count)
                .mapToObj(i -> {
                    Category category = new Category();
                    category.setId(UUID.randomUUID().toString());
                    category.setName(names.get(i % names.size()));
                    category.setSort(i + 1);
                    return category;
                })
                .collect(Collectors.toList());
    }

    // 生成帖子数据
    private static List<Post> generatePosts(List<User> users, List<Category> categories) {
        List<String> titles = List.of("初探人工智能", "夏日旅行攻略", "JVM调优实战", "电影推荐合集", "健身计划分享");

        return users.stream()
                .flatMap(user ->
                        IntStream.range(0, ThreadLocalRandom.current().nextInt(2, 4))
                                .mapToObj(i -> {
                                    Post post = new Post();
                                    post.setId(UUID.randomUUID().toString());
                                    post.setTitle(titles.get(ThreadLocalRandom.current().nextInt(titles.size())));
                                    post.setContent("# 这是用户" + user.getName() + "的帖子内容\n包含丰富的文本内容...");
                                    post.setUserId(user.getId());
                                    post.setPublishAt(LocalDateTime.now().minusHours(ThreadLocalRandom.current().nextInt(24 * 7)));
                                    return post;
                                })
                )
                .collect(Collectors.toList());
    }

    // 生成分类-帖子关联
    private static List<CategoryPost> generateCategoryPosts(List<Post> posts, List<Category> categories) {
        return posts.stream()
                .map(post -> {
                    CategoryPost cp = new CategoryPost();
                    cp.setId(UUID.randomUUID().toString());
                    cp.setPostId(post.getId());
                    cp.setCategoryId(categories.get(
                            ThreadLocalRandom.current().nextInt(categories.size())).getId());
                    return cp;
                })
                .collect(Collectors.toList());
    }


    // 生成点赞数据
    private static List<Like> generateLikes(List<User> users, List<Post> posts) {
        return posts.stream()
                .flatMap(post ->
                        users.stream()
                                .filter(u -> ThreadLocalRandom.current().nextBoolean()) // 随机选择部分用户点赞
                                .map(user -> {
                                    Like like = new Like();
                                    like.setId(UUID.randomUUID().toString());
                                    like.setUserId(user.getId());
                                    like.setPostId(post.getId());
                                    like.setCreateAt(LocalDateTime.now().minusMinutes(ThreadLocalRandom.current().nextInt(120)));
                                    return like;
                                })
                )
                .collect(Collectors.toList());
    }


    // 生成树形结构的评论数据
    private static List<Comment> generateComments(List<User> users, List<Post> posts) {
        String[][] levelComments = {
                // 一级评论
                {"非常好的分享！", "内容很实用", "期待更多这样的内容", "完全同意你的观点", "写得真详细"},
                // 二级回复
                {"我也这么认为", "补充一点：", "具体是指哪方面？", "有不同看法：", "感谢补充！"},
                // 三级回复
                {"你是指...", "明白了，谢谢解释", "有数据支持吗？", "这个角度有意思", "👍"}
        };

        List<Comment> allComments = new ArrayList<>();

        for (Post post : posts) {
            List<Comment> postComments = new ArrayList<>();
            int commentCount = ThreadLocalRandom.current().nextInt(3, 6); // 每篇帖子3-5条一级评论

            // 生成一级评论（楼中楼的首层）
            for (int i = 0; i < commentCount; i++) {
                Comment topLevel = createComment(
                        users,
                        post.getId(),
                        "0", // 一级评论parentId为0
                        levelComments[0][ThreadLocalRandom.current().nextInt(levelComments[0].length)],
                        null // 顶级评论不需要回复对象
                );
                postComments.add(topLevel);
                allComments.add(topLevel);

                // 生成二级回复（回复一级评论）
                int replyCount1 = ThreadLocalRandom.current().nextInt(0, 3); // 每条一级评论0-2条回复
                for (int j = 0; j < replyCount1; j++) {
                    User replyUser = getRandomUser(users, topLevel.getUserId()); // 确保不是楼主自己
                    Comment secondLevel = createComment(
                            users,
                            post.getId(),
                            topLevel.getId(), // 二级回复的parentId是一级评论ID
                            levelComments[1][ThreadLocalRandom.current().nextInt(levelComments[1].length)],
                            topLevel // 引用的一级评论
                    );
                    postComments.add(secondLevel);
                    allComments.add(secondLevel);

                    // 生成三级回复（回复二级评论）
                    if (ThreadLocalRandom.current().nextBoolean()) { // 50%概率生成三级回复
                        User replyUser2 = getRandomUser(users, secondLevel.getUserId());
                        Comment thirdLevel = createComment(
                                users,
                                post.getId(),
                                secondLevel.getId(), // 三级回复parentId是二级评论ID
                                levelComments[2][ThreadLocalRandom.current().nextInt(levelComments[2].length)],
                                secondLevel // 引用的二级评论
                        );
                        postComments.add(thirdLevel);
                        allComments.add(thirdLevel);
                    }
                }
            }
        }
        return allComments;
    }

    // 创建评论对象（支持嵌套回复）
    private static Comment createComment(List<User> users, String postId, String parentId, String content, Comment replyTarget) {
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID().toString());
        comment.setParentId(parentId);

        // 添加@回复功能
        if (replyTarget != null) {
            User targetUser = users.stream()
                    .filter(u -> u.getId().equals(replyTarget.getUserId()))
                    .findFirst()
                    .orElse(null);

            if (targetUser != null) {
                content = "@" + targetUser.getName() + " " + content;
            }
        }

        comment.setContent(content);
        comment.setUserId(users.get(ThreadLocalRandom.current().nextInt(users.size())).getId());
        comment.setPostId(postId);
        comment.setCreateAt(LocalDateTime.now().minusHours(ThreadLocalRandom.current().nextInt(24)));
        return comment;
    }

    // 获取随机用户（排除特定用户避免自己回复自己）
    private static User getRandomUser(List<User> users, String excludeUserId) {
        List<User> validUsers = users.stream()
                .filter(u -> !u.getId().equals(excludeUserId))
                .collect(Collectors.toList());

        return validUsers.get(ThreadLocalRandom.current().nextInt(validUsers.size()));
    }
}
