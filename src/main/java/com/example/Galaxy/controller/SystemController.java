package com.example.Galaxy.controller;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.example.Galaxy.entity.SysUserRole;
import com.example.Galaxy.entity.User;
import com.example.Galaxy.exception.GalaxyException;
import com.example.Galaxy.service.SystemService;
import com.example.Galaxy.exception.CodeEnums;
import com.example.Galaxy.service.UserService;
import com.example.Galaxy.util.JWTUtil;
import com.example.Galaxy.util.Result;
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

    @Autowired
    private SystemService systemService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequiresRoles(value = {"admin"})
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object getUserRole() {
        try {
            List<User> list = systemService.selectAllUserWithRoles();
            List<Map<String, Object>> userList = new ArrayList<>();
            for (User user : list) {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("userId", user.getUserId());
                userMap.put("name", user.getName());
                userMap.put("email", user.getEmail());
                userMap.put("role", user.getRoleList().size() == 0 ? null : user.getRoleList().get(0));
                userMap.put("radio", user.getRoleList().size() == 0 ? 3 : user.getRoleList().get(0).getId());
                userMap.put("account", user.getAccount());
                userList.add(userMap);
            }
            return Result.SUCCESS(userList);
        } catch (Exception e) {
            throw new GalaxyException(CodeEnums.EXCEPTION.getCode(), CodeEnums.EXCEPTION.getMessage());
        }
    }

    @ResponseBody
    @RequiresUser
    @RequestMapping(value = "/myRole", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object getMyRole(HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);
        return Result.SUCCESS(systemService.selectRoleByUserId(userId));
    }

    @ResponseBody
    @RequiresRoles(value = {"admin"})
    @RequestMapping(value = "/getRoleWithPrivilege", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object getAllRole() {
        return Result.SUCCESS(systemService.selectAllRoleWIthPrivilege());
    }

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
            throw new GalaxyException(CodeEnums.MISS_INFO.getCode(), CodeEnums.MISS_INFO.getMessage());
        }
        User user = new User();
        user.setUserId(userId);
        user.setName(name);
        user.setAccount(account);
        user.setEmail(email);
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(roleId);
        sysUserRole.setUserId(userId);
        systemService.updateRoleIdByUserId(sysUserRole);
        userService.updateSelective(user);
        return Result.SUCCESS();
    }
}
