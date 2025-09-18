package com.eq.doc.dto.bank.card;

import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * create time 2025/9/18 22:12
 * {@link com.eq.doc.domain.save.SaveBank}
 *
 * @author xuejiaming
 */
@Data
@SuppressWarnings("EasyQueryFieldMissMatch")
public class BankUpdateRequest {
    private String id;
    private String name;
    private String address;

    /**
     * 银行办法的银行卡
     **/
    private List<InternalSaveBankCards> saveBankCards;


    /**
     * {@link com.eq.doc.domain.save.SaveBankCard}
     **/
    @Data
    public static class InternalSaveBankCards {
        private String id;
        private String type;
        private String code;
    }
}