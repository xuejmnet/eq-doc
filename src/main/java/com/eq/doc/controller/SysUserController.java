package com.eq.doc.controller;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.annotation.EasyQueryTrack;
import com.eq.doc.domain.save.SysRole;
import com.eq.doc.domain.save.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
    @PostMapping("/create")
    @Transactional(rollbackFor = Exception.class)
    @EasyQueryTrack
    public Object create() {
        ArrayList<SysRole> sysRoles = new ArrayList<>();
        {

            SysRole sysRole = new SysRole();
            sysRole.setName("管理员");
            sysRole.setCreateTime(LocalDateTime.now());
            sysRoles.add(sysRole);
        }
        {

            SysRole sysRole = new SysRole();
            sysRole.setName("游客");
            sysRole.setCreateTime(LocalDateTime.now());
            sysRoles.add(sysRole);
        }
        SysUser sysUser = new SysUser();
        sysUser.setName("小明");
        sysUser.setAge(18);
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setSysRoleList(sysRoles);

        easyEntityQuery.savable(sysRoles).executeCommand();
        easyEntityQuery.savable(sysUser).executeCommand();
        return "ok";
    }
}
