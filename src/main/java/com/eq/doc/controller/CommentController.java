package com.eq.doc.controller;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.eq.doc.domain.Comment;
import com.eq.doc.domain.Post;
import com.eq.doc.dto.comment.MyCommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/8/25 22:34
 * 文件说明
 *
 * @author xuejiaming
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/comment")
public class CommentController {
    private final EasyEntityQuery easyEntityQuery;

    @PostMapping("/commentList")
    public List<Comment> commentList() {
        return easyEntityQuery.queryable(Comment.class)
                .where(t_comment -> t_comment.parentId().eq("0"))
                .asTreeCTE()
                .toList();
    }

    @PostMapping("/commentList2")
    public List<Comment> commentList2() {
        return easyEntityQuery.queryable(Comment.class)
                .where(t_comment -> t_comment.parentId().eq("0"))
                .asTreeCTE()
                .toTreeList();
    }

    @PostMapping("/commentList3")
    public List<MyCommentDTO> commentList3() {
        return easyEntityQuery.queryable(Comment.class)
                .where(t_comment -> t_comment.parentId().eq("0"))
                .asTreeCTE()
                .selectAutoInclude(MyCommentDTO.class)
                .toTreeList();
    }
}
