package com.example.Galaxy.controller;


import com.auth0.jwt.JWT;
import com.example.Galaxy.entity.User;
import com.example.Galaxy.service.SystemService;
import com.example.Galaxy.exception.CodeEnums;
import com.example.Galaxy.util.JWTUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @ResponseBody
    @RequiresRoles(value = {"admin"})
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object getUserRole(HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);
        User user = null;
        try {
            user = systemService.selectRoleAndPrivilegeByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @ResponseBody
    @RequiresRoles(value = {"admin"})
    @RequestMapping(value = "/role", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object test(HttpServletRequest httpServletRequest) {
        return systemService.selcetRole(1L);
    }
}
