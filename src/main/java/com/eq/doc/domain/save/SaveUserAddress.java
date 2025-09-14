package com.eq.doc.domain.save;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.eq.doc.configuration.UUIDPrimaryKey;
import com.eq.doc.domain.save.proxy.SaveUserAddressProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/9/14 12:09
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_save_user_addr")
@Data
@EntityProxy
@FieldNameConstants
@EasyAlias("save_user_addr")
public class SaveUserAddress implements ProxyEntityAvailable<SaveUserAddress , SaveUserAddressProxy> {
    @Column(primaryKey = true, primaryKeyGenerator = UUIDPrimaryKey.class)
    private String id;
    private String uid;
    private String province;
    private String city;
    private String area;
    private String address;

    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {Fields.uid}, targetProperty = {SaveUser.Fields.id})
    private SaveUser saveUser;

    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {Fields.uid}, targetProperty = {SaveUserExt.Fields.id})
    private SaveUserExt saveUserExt;
}
