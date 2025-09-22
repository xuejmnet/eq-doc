package com.eq.doc.dto.user;

import lombok.Data;

import java.util.List;

/**
 * create time 2025/9/21 19:49
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class UserRoleRequest {
    private String userId;
    private List<String> roleIds;
}
