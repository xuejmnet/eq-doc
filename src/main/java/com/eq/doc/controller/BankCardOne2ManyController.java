package com.eq.doc.controller;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.annotation.EasyQueryTrack;
import com.eq.doc.domain.save.SaveBank;
import com.eq.doc.domain.save.SaveBankCard;
import com.eq.doc.domain.save.SaveUser;
import com.eq.doc.domain.save.SaveUserAddress;
import com.eq.doc.domain.save.SaveUserExt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/9/14 23:02
 * 文件说明
 *
 * @author xuejiaming
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/bankCardOne2Many")
public class BankCardOne2ManyController {
    private final EasyEntityQuery easyEntityQuery;
    @PostMapping("/create")
    @Transactional(rollbackFor = Exception.class)
    @EasyQueryTrack
    public Object create() {

        SaveBank saveBank = new SaveBank();
        saveBank.setName("工商银行");
        saveBank.setAddress("城市广场1号");
        ArrayList<SaveBankCard> saveBankCards = new ArrayList<>();
        saveBank.setSaveBankCards(saveBankCards);
        SaveBankCard card1 = new SaveBankCard();
        card1.setType("储蓄卡");
        card1.setCode("123");
        saveBankCards.add(card1);
        SaveBankCard card2 = new SaveBankCard();
        card2.setType("信用卡");
        card2.setCode("456");
        saveBankCards.add(card2);

        easyEntityQuery.savable(saveBank).executeCommand();
        return "ok";
    }
    @PostMapping("/bankDelete")
    @Transactional(rollbackFor = Exception.class)
    @EasyQueryTrack
    public Object bankDelete() {

        easyEntityQuery.deletable(SaveUser.class)
                .where(save_user -> save_user.id().isNotNull())
                .allowDeleteStatement(true)
                .executeRows();
        easyEntityQuery.deletable(SaveBank.class)
                .where(save_user -> save_user.id().isNotNull())
                .allowDeleteStatement(true)
                .executeRows();
        easyEntityQuery.deletable(SaveBankCard.class)
                .where(save_user -> save_user.id().isNotNull())
                .allowDeleteStatement(true)
                .executeRows();
        return "ok";
    }


    @PostMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    @EasyQueryTrack
    public Object update() {

        SaveBank saveBank = easyEntityQuery.queryable(SaveBank.class)
                .includes(save_bank -> save_bank.saveBankCards())
                .whereById("dbadf934e3cb4666a5e7689f3a675e83").singleNotNull();

        //假如请求有3个bankcards,其中yi个有id另外yi个没有id
        List<SaveBankCard> list = easyEntityQuery.queryable(SaveBankCard.class)
                .whereByIds(Arrays.asList("724929f91faf404caced57e18da50578"))
                .toList();
        SaveBankCard saveBankCard = new SaveBankCard();//这个是新增的
        saveBankCard.setType("储蓄卡");
        saveBankCard.setCode("789");
        list.add(saveBankCard);

        saveBank.setSaveBankCards(list);
        easyEntityQuery.savable(saveBank).executeCommand();
        return "ok";
    }
}
