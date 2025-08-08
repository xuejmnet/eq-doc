package com.eq.doc.dto.post;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.NavigateFlat;
import com.easy.query.core.annotation.OrderByProperty;
import com.easy.query.core.enums.RelationTypeEnum;
import com.eq.doc.domain.Comment;
import com.eq.doc.domain.proxy.CommentProxy;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/8/6 22:45
 * {@link com.eq.doc.domain.Post}
 *
 * @author xuejiaming
 */
@Data
public class PostPage8Response {
    private String id;
    private String title;
    private String content;
    private String userId;
    private LocalDateTime publishAt;

    @NavigateFlat(pathAlias = "user.id")
    private String userName;

    /**
     * 评论信息
     **/
    @Navigate(value = RelationTypeEnum.OneToMany,orderByProps = {
            @OrderByProperty(property = "createAt",asc = true)
    },limit = 3)
    private List<InternalComment> commentList;

    /**
     * {@link Comment}
     **/
    @Data
    public static class InternalComment {
        private String id;
        private String parentId;
        private String content;
        private LocalDateTime createAt;
    }

}
