package com.eq.doc.domain.save;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.CascadeTypeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.eq.doc.configuration.UUIDPrimaryKey;
import com.eq.doc.domain.save.proxy.SysUserProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/9/11 22:25
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_user")
@Data
@EntityProxy
@FieldNameConstants
@EasyAlias("user")
public class SysUser implements ProxyEntityAvailable<SysUser, SysUserProxy> {
    @Column(primaryKey = true, primaryKeyGenerator = UUIDPrimaryKey.class)
    private String id;
    private String companyId;
    private String name;
    private Integer age;
    private LocalDateTime createTime;


    /**
     *
     **/
    @Navigate(value = RelationTypeEnum.ManyToMany,
            selfProperty = {SysUser.Fields.id},
            selfMappingProperty = {UserRole.Fields.userId},
            mappingClass = UserRole.class,
            targetProperty = {SysRole.Fields.id},
            targetMappingProperty = {UserRole.Fields.roleId}, cascade = CascadeTypeEnum.DELETE)
    private List<SysRole> sysRoleList;
}
