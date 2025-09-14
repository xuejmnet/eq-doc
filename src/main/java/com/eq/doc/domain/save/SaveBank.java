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
import com.eq.doc.domain.save.proxy.SaveBankProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * create time 2025/9/14 22:20
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_save_bank")
@Data
@EntityProxy
@FieldNameConstants
@EasyAlias("save_bank")
public class SaveBank implements ProxyEntityAvailable<SaveBank, SaveBankProxy> {
    @Column(primaryKey = true, primaryKeyGenerator = UUIDPrimaryKey.class)
    private String id;
    private String name;
    private String address;

    /**
     * 银行办法的银行卡
     **/
    @Navigate(value = RelationTypeEnum.OneToMany,
            selfProperty = {SaveBank.Fields.id},
            targetProperty = {SaveBankCard.Fields.bankId},
            cascade = CascadeTypeEnum.DELETE)
    private List<SaveBankCard> saveBankCards;
}
