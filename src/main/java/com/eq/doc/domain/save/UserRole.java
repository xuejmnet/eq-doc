package com.eq.doc.domain.save;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.eq.doc.configuration.UUIDPrimaryKey;
import com.eq.doc.domain.save.proxy.UserRoleProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/9/11 22:26
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_user_role")
@Data
@EntityProxy
@FieldNameConstants
@EasyAlias("user_role")
public class UserRole implements ProxyEntityAvailable<UserRole , UserRoleProxy> {
    @Column(primaryKey = true,primaryKeyGenerator = UUIDPrimaryKey.class)
    private String id;
    private String userId;
    private String roleId;
}
