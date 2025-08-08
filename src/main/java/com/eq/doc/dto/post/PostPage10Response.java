package com.eq.doc.dto.post;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.NavigateFlat;
import com.easy.query.core.annotation.OrderByProperty;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.parser.core.extra.ExtraAutoIncludeConfigure;
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
@EntityProxy
public class PostPage10Response {
    private String id;
    private String title;
    private String content;
    private String userId;
    private LocalDateTime publishAt;

    private String userName;

    private String commentContent;

}
