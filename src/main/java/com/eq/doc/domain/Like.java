package com.eq.doc.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EasyAssertMessage;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.eq.doc.domain.proxy.LikeProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2025/8/5 21:18
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_like")
@EntityProxy
@EasyAlias("t_like")
@EasyAssertMessage("未找到对应的点赞信息")
public class Like implements ProxyEntityAvailable<Like , LikeProxy> {
    @Column(primaryKey = true,comment = "评论id")
    private String id;
    @Column(comment = "用户id",nullable = false)
    private String userId;
    @Column(comment = "帖子id",nullable = false)
    private String postId;
    @Column(comment = "点赞时间")
    private LocalDateTime createAt;
}
