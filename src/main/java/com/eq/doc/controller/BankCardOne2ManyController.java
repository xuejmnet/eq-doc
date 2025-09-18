package com.eq.doc.controller;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.annotation.EasyQueryTrack;
import com.eq.doc.domain.save.SaveBank;
import com.eq.doc.domain.save.SaveBankCard;
import com.eq.doc.domain.save.SaveUser;
import com.eq.doc.domain.save.SaveUserAddress;
import com.eq.doc.domain.save.SaveUserExt;
import com.eq.doc.dto.bank.card.BankUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Object update(@RequestBody BankUpdateRequest request) {

        SaveBank saveBank = easyEntityQuery.queryable(SaveBank.class)
                .includes(save_bank -> save_bank.saveBankCards())
                .whereById(request.getId()).singleNotNull();

        saveBank.setName(request.getName());
        saveBank.setAddress(request.getAddress());

        Set<String> requestIds = request.getSaveBankCards().stream().map(o -> o.getId()).filter(o -> o != null).collect(Collectors.toSet());
        //移除不需要的银行卡
        saveBank.getSaveBankCards().removeIf(o -> !requestIds.contains(o.getId()));
        Map<String, SaveBankCard> bankCardMap = saveBank.getSaveBankCards().stream().collect(Collectors.toMap(o -> o.getId(), o -> o));
        ArrayList<SaveBankCard> newCards = new ArrayList<>();
        for (BankUpdateRequest.InternalSaveBankCards saveBankCard : request.getSaveBankCards()) {
            SaveBankCard dbBankCard = bankCardMap.get(saveBankCard.getId());
            if (dbBankCard == null) {
                SaveBankCard bankCard = new SaveBankCard();
                bankCard.setId(saveBankCard.getId());
                bankCard.setType(saveBankCard.getType());
                bankCard.setCode(saveBankCard.getCode());
                newCards.add(bankCard);
            } else {
                dbBankCard.setType(saveBankCard.getType());
                dbBankCard.setCode(saveBankCard.getCode());
            }
        }
        saveBank.getSaveBankCards().addAll(newCards);

        easyEntityQuery.savable(saveBank).executeCommand();
        return "ok";
    }


    @PostMapping("/update2")
    @Transactional(rollbackFor = Exception.class)
    @EasyQueryTrack
    public Object update2(@RequestBody BankUpdateRequest request) {

        SaveBank saveBank = easyEntityQuery.queryable(SaveBank.class)
                .includes(save_bank -> save_bank.saveBankCards())
                .whereById(request.getId()).singleNotNull();

        saveBank.setName(request.getName());
        saveBank.setAddress(request.getAddress());

        ArrayList<SaveBankCard> requestBankCards = new ArrayList<>();
        for (BankUpdateRequest.InternalSaveBankCards saveBankCard : request.getSaveBankCards()) {
            SaveBankCard bankCard = new SaveBankCard();
            bankCard.setId(saveBankCard.getId());
            bankCard.setType(saveBankCard.getType());
            bankCard.setCode(saveBankCard.getCode());
            requestBankCards.add(bankCard);
        }
        //将右边的集合合并到左侧,左侧会自动根据主键或者自定义匹配值进行匹配，默认根据主键来进行匹配
        easyEntityQuery.mergeCollection(saveBank.getSaveBankCards(), requestBankCards);
        easyEntityQuery.savable(saveBank).executeCommand();
        return "ok";
    }
}
