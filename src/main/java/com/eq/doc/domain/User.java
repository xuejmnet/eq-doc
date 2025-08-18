package com.eq.doc.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EasyAssertMessage;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.eq.doc.domain.proxy.CommentProxy;
import com.eq.doc.domain.proxy.LikeProxy;
import com.eq.doc.domain.proxy.PostProxy;
import com.eq.doc.domain.proxy.UserProxy;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/8/4 22:53
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_user")
@EntityProxy
@EasyAlias("t_user")
@EasyAssertMessage("未找到对应的用户信息")
public class User implements ProxyEntityAvailable<User, UserProxy> {
    @Column(primaryKey = true, comment = "用户id")
    private String id;
    @Column(comment = "用户姓名")
    private String name;
    @Column(comment = "用户手机")
    private String phone;
    @Column(comment = "创建时间")
    private LocalDateTime createAt;

    /**
     * 用户发布的帖子集合
     **/
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {UserProxy.Fields.id}, targetProperty = {PostProxy.Fields.userId})
    private List<Post> posts;

    /**
     * 用户评论集合
     **/
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {UserProxy.Fields.id}, targetProperty = {CommentProxy.Fields.userId})
    private List<Comment> comments;

    /**
     * 用户点赞数
     **/
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {UserProxy.Fields.id}, targetProperty = {LikeProxy.Fields.userId})
    private List<Like> likes;
}
