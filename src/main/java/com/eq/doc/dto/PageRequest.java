package com.eq.doc.dto;

import lombok.Data;

/**
 * create time 2025/8/6 13:31
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class PageRequest {
    private Integer pageIndex=1;
    private Integer pageSize=5;
}
