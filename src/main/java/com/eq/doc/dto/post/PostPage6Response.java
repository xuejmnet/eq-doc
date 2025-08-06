package com.eq.doc.dto.post;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.NavigateFlat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2025/8/6 22:45
 * {@link com.eq.doc.domain.Post}
 *
 * @author xuejiaming
 */
@Data
public class PostPage6Response {
    private String id;
    private String title;
    private String content;
    private String userId;
    private LocalDateTime publishAt;

    @NavigateFlat(pathAlias = "user.id")
    private String userName;
}
