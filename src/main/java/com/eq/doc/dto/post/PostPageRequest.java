package com.eq.doc.dto.post;

import com.eq.doc.dto.PageRequest;
import lombok.Data;

/**
 * create time 2025/8/6 13:31
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class PostPageRequest extends PageRequest {
    private String title;
}
