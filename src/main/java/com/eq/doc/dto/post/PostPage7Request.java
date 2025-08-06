package com.eq.doc.dto.post;

import com.eq.doc.dto.PageRequest;
import lombok.Data;

import java.util.List;

/**
 * create time 2025/8/6 13:31
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class PostPage7Request extends PageRequest {
    private String title;

    private String userName;

}
