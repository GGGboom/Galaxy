package com.example.Galaxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.example.Galaxy.util.annotation.LogAnnotation;
import com.example.Galaxy.util.JWTUtil;
import com.example.Galaxy.util.JsonResult;
import com.example.Galaxy.util.enums.OperationType;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/record")
public class LedgerController {
    private final static Logger logger = Logger.getLogger(LedgerController.class);


    @ResponseBody
    @RequiresRoles(value = {"admin"})
    @LogAnnotation(description = "插入或者更新记录",operationType = OperationType.UPDATE)
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object updateRecord(@RequestBody JSONObject params, HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);

        return JsonResult.SUCCESS();
    }


    @ResponseBody
    @RequiresRoles(value = {"admin"})
    @LogAnnotation(description = "查询所有记录",operationType = OperationType.SELECT)
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object selectAllRecord(@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return JsonResult.SUCCESS();
    }


    @ResponseBody
    @RequiresUser
    @LogAnnotation(description = "查询我的记录",operationType = OperationType.SELECT)
    @RequestMapping(value = "/mine", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object selectMine(HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);
        return JsonResult.SUCCESS();
    }
}
