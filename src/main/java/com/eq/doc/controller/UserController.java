package com.eq.doc.controller;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.util.EasyStringUtil;
import com.eq.doc.domain.Post;
import com.eq.doc.domain.User;
import com.eq.doc.dto.post.PostPageRequest;
import com.eq.doc.dto.user.UserPageRequest;
import com.eq.doc.dto.user.UserPageResponse;
import com.eq.doc.dto.user.UserWithPostResponse;
import com.eq.doc.dto.user.proxy.UserPageResponseProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/8/17 16:54
 * 文件说明
 *
 * @author xuejiaming
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/user")
public class UserController {
    private final EasyEntityQuery easyEntityQuery;


    @PostMapping("/page")
    public EasyPageResult<UserPageResponse> page(@RequestBody UserPageRequest request) {
        LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime nextDay = today.plusDays(1);
        return easyEntityQuery.queryable(User.class)
                //添加这一行使用subQueryToGroupJoin
                .configure(s->s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(t_user -> {
                    //要求用户必须存在一个帖子是这个时间段发布的
                    t_user.posts().any(t_post -> {
                        t_post.publishAt().rangeClosed(request.getPostPublishTimeBegin(), request.getPostPublishTimeEnd());
                    });
                })
                .select(t_user -> {
                    return new UserPageResponseProxy()
                            .id().set(t_user.id())
                            .name().set(t_user.name())
                            .postCount().set(t_user.posts().count()) // 发帖数
                            .todayPostCount().set(t_user.posts().where(p -> p.publishAt().rangeClosedOpen(today, nextDay)).count()) // 发帖数
                            .commentCount().set(t_user.comments().count()) // 评论数
                            .likeCount().set(t_user.likes().count()) // 点赞数
                            .recentlyPostId().set(t_user.posts().orderBy(p->p.publishAt().desc()).first().id()) // 最近发布的帖子id
                            .recentlyPostTitle().set(t_user.posts().orderBy(p->p.publishAt().desc()).first().title()); // 最近发布的帖子标题
                })
                .orderBy(t_user -> t_user.likeCount().desc())
                .toPageResult(request.getPageIndex(), request.getPageSize());
    }

    @PostMapping("/list")
    public List<UserWithPostResponse> list() {
        return easyEntityQuery.queryable(User.class)
                .selectAutoInclude(UserWithPostResponse.class)
                .toList();
    }
}
