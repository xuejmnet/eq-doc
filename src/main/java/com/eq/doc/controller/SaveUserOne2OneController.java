package com.eq.doc.controller;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.api.proxy.entity.save.OwnershipPolicyEnum;
import com.easy.query.core.annotation.EasyQueryTrack;
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
import java.util.List;
import java.util.Random;

/**
 * create time 2025/9/14 15:12
 * 文件说明
 *
 * @author xuejiaming
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/saveUserOne2One")
public class SaveUserOne2OneController {
    private final EasyEntityQuery easyEntityQuery;
    @PostMapping("/create")
    @Transactional(rollbackFor = Exception.class)
    @EasyQueryTrack
    public Object create() {
        SaveUser saveUser = new SaveUser();
        saveUser.setId("1");
        saveUser.setName("小明");
        saveUser.setAge(19);
        saveUser.setCreateTime(LocalDateTime.now());

        SaveUserExt saveUserExt = new SaveUserExt();
        saveUserExt.setId("1");
        saveUserExt.setMoney(BigDecimal.ZERO);
        saveUserExt.setHealthy(true);
        saveUser.setSaveUserExt(saveUserExt);

        SaveUserAddress saveUserAddress = new SaveUserAddress();
        saveUserAddress.setId("3");
        saveUserAddress.setProvince("浙江省");
        saveUserAddress.setCity("绍兴市");
        saveUserAddress.setArea("越城区");
        saveUserAddress.setAddress("鲁迅故居东面");
        saveUser.setSaveUserAddress(saveUserAddress);
        easyEntityQuery.savable(saveUser).executeCommand();
        return "ok";
    }


    @PostMapping("/updateRemove")
    @Transactional(rollbackFor = Exception.class)
    @EasyQueryTrack
    public Object updateRemove() {

        SaveUser saveUser = easyEntityQuery.queryable(SaveUser.class)
                .where(save_user -> {
                    save_user.name().eq("小明");
                })
                .include(save_user -> save_user.saveUserAddress())
                .singleNotNull();
        saveUser.setAge(new Random().nextInt());
        SaveUserAddress saveUserAddress = saveUser.getSaveUserAddress();
        saveUserAddress.setAddress("鲁迅故居西面");

        easyEntityQuery.savable(saveUser).executeCommand();
        return "ok";
    }


    @PostMapping("/remove1")
    @Transactional(rollbackFor = Exception.class)
    @EasyQueryTrack
    public Object remove1() {

        SaveUser saveUser = easyEntityQuery.queryable(SaveUser.class)
                .where(save_user -> {
                    save_user.name().eq("小明");
                })
                .include(save_user -> save_user.saveUserAddress())
                .singleNotNull();
        saveUser.setAge(new Random().nextInt());
        saveUser.setSaveUserAddress(null);

        easyEntityQuery.savable(saveUser).executeCommand();
        return "ok";
    }
    @PostMapping("/remove2")
    @Transactional(rollbackFor = Exception.class)
    @EasyQueryTrack
    public Object remove2() {

        SaveUser saveUser = easyEntityQuery.queryable(SaveUser.class)
                .where(save_user -> {
                    save_user.name().eq("小明");
                })
                .include(save_user -> save_user.saveUserExt())
                .singleNotNull();
        saveUser.setAge(new Random().nextInt());
        saveUser.setSaveUserExt(null);

        easyEntityQuery.savable(saveUser).executeCommand();
        return "ok";
    }
    @PostMapping("/change1")
    @Transactional(rollbackFor = Exception.class)
    @EasyQueryTrack
    public Object change1() {

        SaveUser saveUser = easyEntityQuery.queryable(SaveUser.class)
                .where(save_user -> {
                    save_user.name().eq("小明");
                })
                .include(save_user -> save_user.saveUserAddress())
                .singleNotNull();
        saveUser.setAge(new Random().nextInt());
        SaveUserAddress saveUserAddress = new SaveUserAddress();
        saveUserAddress.setId("4");
        saveUserAddress.setProvince("浙江省");
        saveUserAddress.setCity("绍兴市");
        saveUserAddress.setArea("越城区");
        saveUserAddress.setAddress("鲁迅故居南面");
        saveUser.setSaveUserAddress(saveUserAddress);

        easyEntityQuery.savable(saveUser).executeCommand();
        return "ok";
    }


    @PostMapping("/deleteAll")
    @Transactional(rollbackFor = Exception.class)
    @EasyQueryTrack
    public Object deleteAll() {

        easyEntityQuery.deletable(SaveUser.class)
                .where(save_user -> save_user.id().isNotNull())
                .allowDeleteStatement(true)
                .executeRows();
        easyEntityQuery.deletable(SaveUserExt.class)
                .where(save_user -> save_user.id().isNotNull())
                .allowDeleteStatement(true)
                .executeRows();
        easyEntityQuery.deletable(SaveUserAddress.class)
                .where(save_user -> save_user.id().isNotNull())
                .allowDeleteStatement(true)
                .executeRows();

        return "ok";
    }


    @PostMapping("/create2")
    @Transactional(rollbackFor = Exception.class)
    @EasyQueryTrack
    public Object create2() {
        ArrayList<SaveUser> users = new ArrayList<>();
        {

            SaveUser saveUser = new SaveUser();
            users.add(saveUser);
            saveUser.setId("小明1的id");
            saveUser.setName("小明1");
            saveUser.setAge(19);
            saveUser.setCreateTime(LocalDateTime.now());

            SaveUserExt saveUserExt = new SaveUserExt();
            saveUserExt.setMoney(BigDecimal.ZERO);
            saveUserExt.setHealthy(true);
            saveUser.setSaveUserExt(saveUserExt);

            SaveUserAddress saveUserAddress = new SaveUserAddress();
            saveUserAddress.setId("小明1的家的id");
            saveUserAddress.setProvince("浙江省");
            saveUserAddress.setCity("绍兴市");
            saveUserAddress.setArea("越城区");
            saveUserAddress.setAddress("小明1的家");
            saveUser.setSaveUserAddress(saveUserAddress);
        }
        {

            SaveUser saveUser = new SaveUser();
            users.add(saveUser);
            saveUser.setId("小明2的id");
            saveUser.setName("小明2");
            saveUser.setAge(19);
            saveUser.setCreateTime(LocalDateTime.now());

            SaveUserExt saveUserExt = new SaveUserExt();
            saveUserExt.setMoney(BigDecimal.ZERO);
            saveUserExt.setHealthy(true);
            saveUser.setSaveUserExt(saveUserExt);

            SaveUserAddress saveUserAddress = new SaveUserAddress();
            saveUserAddress.setId("小明2的家的id");
            saveUserAddress.setProvince("浙江省");
            saveUserAddress.setCity("绍兴市");
            saveUserAddress.setArea("越城区");
            saveUserAddress.setAddress("小明2的家");
            saveUser.setSaveUserAddress(saveUserAddress);
        }
        easyEntityQuery.savable(users).executeCommand();
        return "ok";
    }
    @PostMapping("/change2")
    @Transactional(rollbackFor = Exception.class)
    @EasyQueryTrack
    public Object change2() {

        List<SaveUser> list = easyEntityQuery.queryable(SaveUser.class)
                .include(save_user -> save_user.saveUserAddress())
                .toList();
        SaveUser xm1 = list.get(0);
        SaveUserAddress xm1address = xm1.getSaveUserAddress();
        SaveUser xm2 = list.get(1);
        SaveUserAddress xm2address = xm2.getSaveUserAddress();
        xm1.setSaveUserAddress(xm2address);
        xm2.setSaveUserAddress(xm1address);

        easyEntityQuery.savable(list).ownershipPolicy(OwnershipPolicyEnum.AllowOwnershipChange).executeCommand();
        return "ok";
    }

}
