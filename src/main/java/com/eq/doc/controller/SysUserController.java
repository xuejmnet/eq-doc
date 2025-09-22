package com.eq.doc.controller;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.annotation.EasyQueryTrack;
import com.eq.doc.domain.save.SysRole;
import com.eq.doc.domain.save.SysUser;
import com.eq.doc.domain.save.UserRole;
import com.eq.doc.dto.user.UserRoleRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/9/11 22:28
 * 文件说明
 *
 * @author xuejiaming
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/sysUser")
public class SysUserController {
    private final EasyEntityQuery easyEntityQuery;
    private final TransactionTemplate transactionTemplate;
    @PostMapping("/create")
    @EasyQueryTrack
    public Object create() {
        ArrayList<SysRole> sysRoles = new ArrayList<>();
        {
            SysRole sysRole = new SysRole();
            sysRole.setId("r1");
            sysRole.setName("管理员");
            sysRole.setCreateTime(LocalDateTime.now());
            sysRoles.add(sysRole);
        }
        {
            SysRole sysRole = new SysRole();
            sysRole.setId("r2");
            sysRole.setName("游客2");
            sysRole.setCreateTime(LocalDateTime.now());
            sysRoles.add(sysRole);
        }
        {

            SysRole sysRole = new SysRole();
            sysRole.setId("r3");
            sysRole.setName("游客3");
            sysRole.setCreateTime(LocalDateTime.now());
            sysRoles.add(sysRole);
        }
        ArrayList<SysUser> sysUsers = new ArrayList<>();

        {
            SysUser sysUser = new SysUser();
            sysUser.setId("u1");
            sysUser.setName("小明");
            sysUser.setAge(18);
            sysUser.setCreateTime(LocalDateTime.now());
            sysUser.setSysRoleList(Arrays.asList(sysRoles.get(0),sysRoles.get(1)));
            sysUsers.add(sysUser);
        }
        {
            SysUser sysUser = new SysUser();
            sysUser.setId("u2");
            sysUser.setName("小红");
            sysUser.setAge(18);
            sysUser.setCreateTime(LocalDateTime.now());
            sysUser.setSysRoleList(Arrays.asList(sysRoles.get(1),sysRoles.get(2)));
            sysUsers.add(sysUser);
        }
        transactionTemplate.execute(status->{
            easyEntityQuery.savable(sysRoles).executeCommand();
            easyEntityQuery.savable(sysUsers).executeCommand();
            return null;
        });
        return "ok";
    }
    @PostMapping("/deleteAll")
    @EasyQueryTrack
    public Object deleteAll() {
        transactionTemplate.execute(status->{
            easyEntityQuery.deletable(SysUser.class).where(user -> user.id().isNotNull()).allowDeleteStatement(true).executeRows();
            easyEntityQuery.deletable(UserRole.class).where(user -> user.id().isNotNull()).allowDeleteStatement(true).executeRows();
            easyEntityQuery.deletable(SysRole.class).where(user -> user.id().isNotNull()).allowDeleteStatement(true).executeRows();
            return null;
        });
        return "ok";
    }
    @PostMapping("/update")
    @EasyQueryTrack
    public Object update(@RequestBody UserRoleRequest request) {
        SysUser sysUser = easyEntityQuery.queryable(SysUser.class)
                .includes(user -> user.sysRoleList())
                .whereById(request.getUserId()).singleNotNull();

        List<SysRole> list = easyEntityQuery.queryable(SysRole.class).whereByIds(request.getRoleIds())
                .toList();
        sysUser.setSysRoleList(list);

        transactionTemplate.execute(status->{
            easyEntityQuery.savable(sysUser).executeCommand();
            return null;
        });

        return "ok";
    }
    @PostMapping("/update2")
    @EasyQueryTrack
    public Object update2(@RequestBody UserRoleRequest request) {
        SysUser sysUser = easyEntityQuery.queryable(SysUser.class)
                .includes(user -> user.userRoleList())
                .whereById(request.getUserId()).singleNotNull();

        List<SysRole> list = easyEntityQuery.queryable(SysRole.class).whereByIds(request.getRoleIds())
                .toList();
        ArrayList<UserRole> userRoles = new ArrayList<>();
        for (SysRole sysRole : list) {
            UserRole userRole = new UserRole();
            userRole.setUserId(sysUser.getId());
            userRole.setRoleId(sysRole.getId());
            userRoles.add(userRole);
        }
        sysUser.setUserRoleList(userRoles);

        transactionTemplate.execute(status->{
            easyEntityQuery.savable(sysUser).executeCommand();
            return null;
        });

        return "ok";
    }


}
