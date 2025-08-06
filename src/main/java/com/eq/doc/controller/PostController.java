package com.eq.doc.controller;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.util.EasyStringUtil;
import com.eq.doc.domain.Post;
import com.eq.doc.dto.post.PostDTO;
import com.eq.doc.dto.post.PostPage3Request;
import com.eq.doc.dto.post.PostPage4Request;
import com.eq.doc.dto.post.PostPage4Response;
import com.eq.doc.dto.post.PostPage6Response;
import com.eq.doc.dto.post.PostPage7Request;
import com.eq.doc.dto.post.PostPageRequest;
import com.eq.doc.dto.post.proxy.PostPage4ResponseProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create time 2025/8/3 21:39
 * 文件说明
 *
 * @author xuejiaming
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/post")
public class PostController {
    private final EasyEntityQuery easyEntityQuery;

    @PostMapping("/page")
    public EasyPageResult<Post> page(@RequestBody PostPageRequest request) {
        return easyEntityQuery.queryable(Post.class)
                .where(t_post -> {
//                    if(EasyStringUtil.isNotBlank(request.getTitle())){
//                        t_post.title().contains(request.getTitle());
//                    }
                    t_post.title().contains(EasyStringUtil.isNotBlank(request.getTitle()), request.getTitle());
                })
                .orderBy(t_post -> t_post.publishAt().desc())
                .toPageResult(request.getPageIndex(), request.getPageSize());
    }

    @PostMapping("/page2")
    public EasyPageResult<Post> page2(@RequestBody PostPageRequest request) {
        return easyEntityQuery.queryable(Post.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .where(t_post -> {
                    t_post.title().contains(request.getTitle());
                })
                .orderBy(t_post -> t_post.publishAt().desc())
                .orderBy(t_post -> t_post.anyColumn("publishAt").orderBy(true))
                .toPageResult(request.getPageIndex(), request.getPageSize());
    }

    @PostMapping("/page3")
    public EasyPageResult<Post> page3(@RequestBody PostPage3Request request) {
        return easyEntityQuery.queryable(Post.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .where(t_post -> {
                    t_post.title().contains(request.getTitle());
                })
                .orderBy(request.getOrders() != null, t_post -> {
                    for (PostPage3Request.InternalOrder order : request.getOrders()) {
                        t_post.anyColumn(order.getProperty()).orderBy(order.isAsc());
                    }
                })
                .toPageResult(request.getPageIndex(), request.getPageSize());
    }

    @PostMapping("/page4")
    public EasyPageResult<Post> page4(@RequestBody PostPage4Request request) {
        return easyEntityQuery.queryable(Post.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .where(t_post -> {
                    t_post.user().filter(u -> {
                        u.phone().ne("123");
                    });
                    t_post.title().contains(request.getTitle());
                    t_post.user().name().contains(request.getUserName());
                })
                .orderBy(request.getOrders() != null, t_post -> {
                    for (PostPage4Request.InternalOrder order : request.getOrders()) {
                        t_post.anyColumn(order.getProperty()).orderBy(order.isAsc());
                    }
                })
                .toPageResult(request.getPageIndex(), request.getPageSize());
    }

    @PostMapping("/page5")
    public EasyPageResult<PostPage4Response> page5(@RequestBody PostPage4Request request) {
        return easyEntityQuery.queryable(Post.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .where(t_post -> {
                    t_post.title().contains(request.getTitle());
                    t_post.user().name().contains(request.getUserName());
                })
                .orderBy(request.getOrders() != null, t_post -> {
                    for (PostPage4Request.InternalOrder order : request.getOrders()) {
                        t_post.anyColumn(order.getProperty()).orderBy(order.isAsc());
                    }
                })
                .select(t_post -> new PostPage4ResponseProxy()
                        .selectAll(t_post).selectIgnores(t_post.title())
                        .userName().set(t_post.user().name())
                )
                .toPageResult(request.getPageIndex(), request.getPageSize());
    }
    @PostMapping("/page6")
    public EasyPageResult<PostPage6Response> page6(@RequestBody PostPage4Request request) {
        return easyEntityQuery.queryable(Post.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .where(t_post -> {
                    t_post.title().contains(request.getTitle());
                    t_post.user().name().contains(request.getUserName());
                })
                .orderBy(request.getOrders() != null, t_post -> {
                    for (PostPage4Request.InternalOrder order : request.getOrders()) {
                        t_post.anyColumn(order.getProperty()).orderBy(order.isAsc());
                    }
                })
                .selectAutoInclude(PostPage6Response.class)
                .toPageResult(request.getPageIndex(), request.getPageSize());
    }
    @PostMapping("/page7")
    public EasyPageResult<Post> page7(@RequestBody PostPage7Request request) {
        return easyEntityQuery.queryable(Post.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .include(t_post -> t_post.user(),uq->{
                    uq.select(u->u.FETCHER.id().name());
                })
                .where(t_post -> {
                    t_post.title().contains(request.getTitle());
                    t_post.user().name().contains(request.getUserName());
                })
                .toPageResult(request.getPageIndex(), request.getPageSize());
    }
    @PostMapping("/selectAutoInclude")
    public List<PostDTO> selectAutoInclude(@RequestBody PostPage7Request request) {
        return easyEntityQuery.queryable(Post.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .include(t_post -> t_post.user(),uq->{
                    uq.select(u->u.FETCHER.id().name());
                })
                .where(t_post -> {
                    t_post.title().contains(request.getTitle());
                    t_post.user().name().contains(request.getUserName());
                })
                .selectAutoInclude(PostDTO.class).toList();
    }
}
