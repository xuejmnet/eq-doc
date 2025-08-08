package com.eq.doc.dto.post;

import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.NavigateFlat;
import com.easy.query.core.annotation.OrderByProperty;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.parser.core.extra.ExtraAutoIncludeConfigure;
import com.easy.query.core.proxy.sql.Select;
import com.eq.doc.domain.Comment;
import com.eq.doc.domain.proxy.CommentProxy;
import com.eq.doc.domain.proxy.PostProxy;
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
public class PostPage11Response {

    private static final ExtraAutoIncludeConfigure EXTRA_AUTO_INCLUDE_CONFIGURE = PostProxy.TABLE.EXTRA_AUTO_INCLUDE_CONFIGURE()
            .select(t_post -> Select.of(
                    t_post.categoryList().joining(cate->cate.name()).as("categoryNames")
            ));

    private String id;
    private String title;
    private String content;
    private String userId;
    private LocalDateTime publishAt;

    @NavigateFlat(pathAlias = "user.id")
    private String userName;

    @SuppressWarnings("EasyQueryFieldMissMatch")
    private String categoryNames;

    /**
     * 评论信息
     **/
    @Navigate(value = RelationTypeEnum.OneToMany, orderByProps = {
            @OrderByProperty(property = "createAt", asc = true)
    }, limit = 3)
    private List<InternalComment> commentList;

    /**
     * {@link Comment}
     **/
    @Data
    public static class InternalComment {


        private static final ExtraAutoIncludeConfigure EXTRA_AUTO_INCLUDE_CONFIGURE = CommentProxy.TABLE.EXTRA_AUTO_INCLUDE_CONFIGURE()
                .where(t_comment -> {
                    t_comment.parentId().eq("0");
                });

        private String id;
        private String parentId;
        private String content;
        private LocalDateTime createAt;
    }

}
