package com.eq.doc.controller;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.annotation.EasyQueryTrack;
import com.eq.doc.domain.save.SaveBank;
import com.eq.doc.domain.save.SaveBankCard;
import com.eq.doc.domain.save.SaveUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * create time 2025/9/21 15:37
 * 文件说明
 *
 * @author xuejiaming
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/bankCardMany2One")
public class BankCardMany2OneController {
    private final EasyEntityQuery easyEntityQuery;
    @PostMapping("/create")
    @Transactional(rollbackFor = Exception.class)
    @EasyQueryTrack
    public Object create() {
        SaveUser saveUser = new SaveUser();
        saveUser.setId("u1");
        saveUser.setAge(20);
        saveUser.setName("小明");

        SaveBank saveBank = new SaveBank();
        saveBank.setId("1");
        saveBank.setName("工商银行");
        saveBank.setAddress("城市广场1号");
        ArrayList<SaveBankCard> saveBankCards = new ArrayList<>();
        saveBank.setSaveBankCards(saveBankCards);
        SaveBankCard card1 = new SaveBankCard();
        card1.setId("2");
        card1.setType("储蓄卡");
        card1.setCode("123");
        saveBankCards.add(card1);
        SaveBankCard card2 = new SaveBankCard();
        card2.setId("3");
        card2.setType("信用卡");
        card2.setCode("456");
        saveBankCards.add(card2);
        SaveBankCard card3 = new SaveBankCard();
        card3.setId("4");
        card3.setType("信用卡");
        card3.setCode("789");
        saveBankCards.add(card3);
        saveUser.setSaveBankCards(saveBankCards);
        easyEntityQuery.savable(saveBank).executeCommand();
        easyEntityQuery.savable(saveUser).executeCommand();
        return "ok";
    }
    @PostMapping("/update1")
    @Transactional(rollbackFor = Exception.class)
    @EasyQueryTrack
    public Object update1() {
        SaveBankCard saveBankCard = easyEntityQuery.queryable(SaveBankCard.class)
                .include(save_bank_card -> save_bank_card.saveBank())
                .whereById("2")
                .singleNotNull();
        saveBankCard.setSaveBank(null);
        easyEntityQuery.savable(saveBankCard).executeCommand();
        return "ok";
    }
    @PostMapping("/update2")
    @Transactional(rollbackFor = Exception.class)
    @EasyQueryTrack
    public Object update2() {
        SaveBankCard saveBankCard = easyEntityQuery.queryable(SaveBankCard.class)
                .include(save_bank_card -> save_bank_card.saveUser())
                .whereById("2")
                .singleNotNull();
        saveBankCard.setSaveUser(null);
        easyEntityQuery.savable(saveBankCard).executeCommand();
        return "ok";
    }
}
