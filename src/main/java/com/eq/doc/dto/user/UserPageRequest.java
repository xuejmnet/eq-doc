package com.eq.doc.dto.user;

import com.eq.doc.dto.PageRequest;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2025/8/17 16:55
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class UserPageRequest extends PageRequest {

    private LocalDateTime postPublishTimeBegin;

    private LocalDateTime postPublishTimeEnd;
}
