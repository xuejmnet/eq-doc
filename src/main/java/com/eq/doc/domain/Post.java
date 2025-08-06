package com.eq.doc.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EasyAssertMessage;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.eq.doc.domain.proxy.PostProxy;
import com.eq.doc.domain.proxy.UserProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2025/8/4 22:53
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_post")
@EntityProxy
@EasyAlias("t_post")
@EasyAssertMessage("未找到对应的帖子信息")
public class Post implements ProxyEntityAvailable<Post, PostProxy> {
    @Column(primaryKey = true, comment = "帖子id")
    private String id;
    @Column(comment = "帖子标题")
    private String title;
    @Column(comment = "帖子内容")
    private String content;
    @Column(comment = "用户id")
    private String userId;
    @Column(comment = "发布时间")
    private LocalDateTime publishAt;

    /**
     * 发帖人
     **/
    @Navigate(value = RelationTypeEnum.ManyToOne,
            selfProperty = {PostProxy.Fields.userId},
            targetProperty = {UserProxy.Fields.id},
            required = true)
    private User user;
}
