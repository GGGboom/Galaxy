package com.example.Galaxy.controller;


import com.auth0.jwt.JWT;
import com.example.Galaxy.service.SystemService;
import com.example.Galaxy.exception.CodeEnums;
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
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object getUserRole(HttpServletRequest httpServletRequest){
        Long userId = Long.parseLong(JWT.decode(httpServletRequest.getHeader("Authorization")).getAudience().get(0));
        try{
            systemService.selectRoleAndPrivilegeByUserId(userId);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error");
        }

        return systemService.selectRoleAndPrivilegeByUserId(userId);
    }

    @ResponseBody
    @RequestMapping(value = "/role", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object test(HttpServletRequest httpServletRequest){
        return systemService.selcetRole(1L);
    }
}
