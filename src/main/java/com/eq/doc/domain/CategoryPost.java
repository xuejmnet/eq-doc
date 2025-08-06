package com.eq.doc.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EasyAssertMessage;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.eq.doc.domain.proxy.CategoryPostProxy;
import lombok.Data;

/**
 * create time 2025/8/5 21:19
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_category_post")
@EntityProxy
@EasyAlias("t_category_post")
@EasyAssertMessage("未找到对应的类目帖子关联信息")
public class CategoryPost implements ProxyEntityAvailable<CategoryPost , CategoryPostProxy> {
    @Column(primaryKey = true,comment = "评论id")
    private String id;
    @Column(comment = "帖子id",nullable = false)
    private String postId;
    @Column(comment = "类目id",nullable = false)
    private String categoryId;
}
