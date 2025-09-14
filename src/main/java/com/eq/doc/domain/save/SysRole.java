package com.eq.doc.domain.save;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.eq.doc.configuration.UUIDPrimaryKey;
import com.eq.doc.domain.save.proxy.SysRoleProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
 * create time 2025/9/11 22:26
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_role")
@Data
@EntityProxy
@FieldNameConstants
@EasyAlias("user")
public class SysRole implements ProxyEntityAvailable<SysRole , SysRoleProxy> {
    @Column(primaryKey = true,primaryKeyGenerator = UUIDPrimaryKey.class)
    private String id;
    private String name;
    private LocalDateTime createTime;
}
