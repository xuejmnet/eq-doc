package com.eq.doc.controller;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.util.EasyStringUtil;
import com.eq.doc.domain.Post;
import com.eq.doc.dto.post.PostPage3Request;
import com.eq.doc.dto.post.PostPageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                    t_post.title().contains(EasyStringUtil.isNotBlank(request.getTitle()),request.getTitle());
                })
                .orderBy(t_post -> t_post.publishAt().desc())
                .toPageResult(request.getPageIndex(),request.getPageSize());
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
                .toPageResult(request.getPageIndex(),request.getPageSize());
    }
    @PostMapping("/page3")
    public EasyPageResult<Post> page3(@RequestBody PostPage3Request request) {
        return easyEntityQuery.queryable(Post.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .where(t_post -> {
                    t_post.title().contains(request.getTitle());
                })
                .orderBy(request.getOrders()!=null,t_post -> {
                    for (PostPage3Request.InternalOrder order : request.getOrders()) {
                        t_post.anyColumn(order.getProperty()).orderBy(order.isAsc());
                    }
                })
                .toPageResult(request.getPageIndex(),request.getPageSize());
    }
}
