package com.eq.doc.domain.save;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.SaveKey;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.eq.doc.configuration.UUIDPrimaryKey;
import com.eq.doc.domain.save.proxy.SaveBankCardProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/9/14 22:20
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_save_bank_card")
@Data
@EntityProxy
@FieldNameConstants
@EasyAlias("save_bank_card")
public class SaveBankCard implements ProxyEntityAvailable<SaveBankCard , SaveBankCardProxy> {
    @Column(primaryKey = true, primaryKeyGenerator = UUIDPrimaryKey.class)
    private String id;
    private String type;
    @SaveKey
    private String code;
    private String uid;
    private String bankId;
}
