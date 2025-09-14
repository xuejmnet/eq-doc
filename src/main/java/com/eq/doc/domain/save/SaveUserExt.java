package com.eq.doc.domain.save;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.eq.doc.configuration.UUIDPrimaryKey;
import com.eq.doc.domain.save.proxy.SaveUserExtProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

/**
 * create time 2025/9/14 12:08
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_save_user_ext")
@Data
@EntityProxy
@FieldNameConstants
@EasyAlias("save_user_ext")
public class SaveUserExt implements ProxyEntityAvailable<SaveUserExt, SaveUserExtProxy> {
    @Column(primaryKey = true, primaryKeyGenerator = UUIDPrimaryKey.class)
    private String id;
    private BigDecimal money;
    private Boolean healthy;


    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {Fields.id}, targetProperty = {SaveUser.Fields.id})
    private SaveUser saveUser;

    /**
     * 用户地址
     **/
    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {SaveUserExt.Fields.id}, targetProperty = {SaveUserAddress.Fields.uid})
    private SaveUserAddress saveUserAddress;
}
