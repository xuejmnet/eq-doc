package com.eq.doc.dto.user;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;

/**
 * create time 2025/8/17 16:56
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class UserPageResponse {
    /**
     * id
     */
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 发帖数
     */
    private Long postCount;
    /**
     * 今日发帖数
     */
    private Long todayPostCount;
    /**
     * 评论数
     */
    private Long commentCount;
    /**
     * 点赞数
     */
    private Long likeCount;
    /**
     * 最近发布的帖子id
     */
    private String recentlyPostId;
    /**
     * 最近发布的帖子标题
     */
    private String recentlyPostTitle;
}
