package com.example.springframework.boot.security.database.web;

import com.example.springframework.boot.security.database.entity.SystemPermission;
import com.example.springframework.boot.security.database.entity.SystemRole;
import com.example.springframework.boot.security.database.entity.SystemUser;
import com.example.springframework.boot.security.database.mapper.SystemPermissionMapper;
import com.example.springframework.boot.security.database.mapper.SystemRoleMapper;
import com.example.springframework.boot.security.database.mapper.SystemUserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/system")
@Api(tags = "system管理接口")
public class SystemController {

    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private SystemRoleMapper systemRoleMapper;
    @Autowired
    private SystemPermissionMapper systemPermissionMapper;

    @ApiOperation(value = "根据用户id查询系统用户信息", produces = MediaType.TEXT_PLAIN_VALUE, notes = "返回：系统用户数据")
    @GetMapping("/user/id")
    public SystemUser getUserById(@RequestParam("id") Long id) {
        //用户信息
        SystemUser systemUser = systemUserMapper.getById(id);
        if (null == systemUser) {
            return null;
        }
        //角色信息
        SystemRole systemRole = systemUserMapper.getRoleByUserId(id);
        //权限信息
        List<SystemPermission> systemPermissionList = systemUserMapper.getPermissonsByUserId(id);
        //封装信息
        if (null != systemRole) {
            systemRole.setSystemPermissions(systemPermissionList);
        }
        systemUser.setSystemRole(systemRole);
        return systemUser;
    }

    @ApiOperation(value = "根据角色id查询系统角色信息", produces = MediaType.TEXT_PLAIN_VALUE, notes = "返回：系统角色数据")
    @GetMapping("/role/id")
    public SystemRole getRoleById(@RequestParam("id") Long id) {
        //角色信息
        SystemRole systemRole = systemRoleMapper.getById(id);
        if (null == systemRole) {
            return null;
        }
        //权限信息
        List<SystemPermission> systemPermissions = systemRoleMapper.getPermissonsByRoleId(id);
        systemRole.setSystemPermissions(systemPermissions);
        return systemRole;
    }

    @ApiOperation(value = "根据权限id查询系统权限信息", produces = MediaType.TEXT_PLAIN_VALUE, notes = "返回：系统权限数据")
    @GetMapping("/permisson/id")
    public SystemPermission getPermissionById(@RequestParam("id")Long id){
        return systemPermissionMapper.getById(id);
    }

}
