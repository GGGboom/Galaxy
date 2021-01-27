package com.example.Galaxy.controller;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.example.Galaxy.entity.SysUser;
import com.example.Galaxy.entity.SysUserRole;
import com.example.Galaxy.exception.GalaxyException;
import com.example.Galaxy.service.SystemService;
import com.example.Galaxy.service.UserService;
import com.example.Galaxy.util.annotation.LogAnnotation;
import com.example.Galaxy.util.enums.ExceptionEnums;
import com.example.Galaxy.util.JWTUtil;
import com.example.Galaxy.util.JsonResult;
import com.example.Galaxy.util.enums.OperationType;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/system")
public class SystemController {
    private final static Logger logger = Logger.getLogger(SystemController.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private UserService userService;

    @LogAnnotation(description = "查询所有用户及其角色",operationType = OperationType.SELECT)
    @ResponseBody
    @RequiresRoles(value = {"admin"})
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object getUserRole() {
        try {
            List<SysUser> list = systemService.selectAllUserWithRoles();
            List<Map<String, Object>> userList = new ArrayList<>();
            for (SysUser sysUser : list) {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("userId", sysUser.getUserId());
                userMap.put("name", sysUser.getName());
                userMap.put("email", sysUser.getEmail());
                userMap.put("role", sysUser.getRoleList().size() == 0 ? null : sysUser.getRoleList().get(0));
                userMap.put("radio", sysUser.getRoleList().size() == 0 ? null : sysUser.getRoleList().get(0).getRoleId());
                userMap.put("account", sysUser.getAccount());
                userList.add(userMap);
            }
            return JsonResult.SUCCESS(userList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GalaxyException(ExceptionEnums.EXCEPTION.getCode(), ExceptionEnums.EXCEPTION.getMessage());
        }
    }

    @LogAnnotation(description = "查询我的角色",operationType = OperationType.SELECT)
    @ResponseBody
    @RequiresUser
    @RequestMapping(value = "/myRole", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object getMyRole(HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);
        return JsonResult.SUCCESS(systemService.selectRoleByUserId(userId));
    }

    @LogAnnotation(description = "查询我的角色和角色对应的权限",operationType = OperationType.SELECT)
    @ResponseBody
    @RequiresRoles(value = {"admin"})
    @RequestMapping(value = "/getRoleWithPrivilege", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object getAllRole() {
        return JsonResult.SUCCESS(systemService.selectAllRoleWIthPrivilege());
    }

    @LogAnnotation(description = "设置用户的角色和权限",operationType = OperationType.UPDATE)
    @ResponseBody
    @RequiresRoles(value = {"admin"})
    @RequestMapping(value = "/setUserRole", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object changeUserRole(@RequestBody JSONObject param) {
        Long userId = param.getLong("userId");
        Long roleId = param.getLong("radio");
        String account = param.getString("account");
        String email = param.getString("email");
        String name = param.getString("name");
        if (userId == null || roleId == null) {
            throw new GalaxyException(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setName(name);
        sysUser.setAccount(account);
        sysUser.setEmail(email);
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(roleId);
        sysUserRole.setUserId(userId);
        systemService.updateRoleIdByUserId(sysUserRole);
        userService.updateSelective(sysUser);
        return JsonResult.SUCCESS();
    }
}
