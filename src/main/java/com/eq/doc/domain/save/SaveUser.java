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
import com.eq.doc.domain.save.proxy.SaveUserProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/9/14 12:07
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_save_user")
@Data
@EntityProxy
@FieldNameConstants
@EasyAlias("save_user")
public class SaveUser implements ProxyEntityAvailable<SaveUser, SaveUserProxy> {
    @Column(primaryKey = true, primaryKeyGenerator = UUIDPrimaryKey.class)
    private String id;
    private String name;
    private Integer age;
    private LocalDateTime createTime;
    /**
     * 用户额外信息
     **/
    @Navigate(value = RelationTypeEnum.OneToOne,
            selfProperty = {SaveUser.Fields.id},
            targetProperty = {SaveUserExt.Fields.id},
            cascade = CascadeTypeEnum.DELETE)//修改为级联删除
    private SaveUserExt saveUserExt;

    /**
     * 用户地址信息
     **/
    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {SaveUser.Fields.id}, targetProperty = {SaveUserAddress.Fields.uid})
    private SaveUserAddress saveUserAddress;

    /**
     * 用户拥有的银行卡
     **/
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {SaveUser.Fields.id}, targetProperty = {SaveBankCard.Fields.uid})
    private List<SaveBankCard> saveBankCards;
}
