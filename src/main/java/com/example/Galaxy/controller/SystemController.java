package com.example.Galaxy.controller;


import com.auth0.jwt.JWT;
import com.example.Galaxy.service.SystemService;
import com.example.Galaxy.util.Result;
import com.example.Galaxy.util.authorization.UserLoginToken;
import com.example.Galaxy.util.exception.CodeEnums;
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

    @UserLoginToken
    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object getUserRole(HttpServletRequest httpServletRequest){
        return systemService.getUserRole();
    }

    @UserLoginToken
    @ResponseBody
    @RequestMapping(value = "/myRole", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object getMyRoleList(HttpServletRequest httpServletRequest){
        Long userId = Long.parseLong(JWT.decode(httpServletRequest.getHeader("Authorization")).getAudience().get(0));
        return new Result(CodeEnums.SUCCESS.getCode(),CodeEnums.SUCCESS.getMessage(),systemService.getRoleListByUserId(userId));
    }
}
