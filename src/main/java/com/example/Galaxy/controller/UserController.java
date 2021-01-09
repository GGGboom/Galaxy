package com.example.Galaxy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.example.Galaxy.entity.SysUserRole;
import com.example.Galaxy.entity.User;
import com.example.Galaxy.service.SystemService;
import com.example.Galaxy.service.UserService;
import com.example.Galaxy.service.impl.RedisService;
import com.example.Galaxy.util.CryptUtil;
import com.example.Galaxy.util.FileUtil;
import com.example.Galaxy.util.JWTUtil;
import com.example.Galaxy.util.Result;
import com.example.Galaxy.exception.CodeEnums;
import com.example.Galaxy.exception.GalaxyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api("用户管理")
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private RedisService redisService;


    @ApiOperation("用户注册")
    @ApiImplicitParam(name = "param", value = "name:必选:String----account:必选:String----password:必选:String", required = true, dataType = "String", paramType = "body")
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object addUser(@RequestBody JSONObject param) {
        String name = param.getString("name");
        String account = param.getString("account");
        String password = param.getString("password");
        String email = param.getString("email");
        Long roleId = param.getLong("radio");
        if (name == null || account == null || password == null) {
            return new Result(CodeEnums.MISS_INFO.getCode(), CodeEnums.MISS_INFO.getMessage());
        }
        try {
            password = CryptUtil.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setName(name);
        user.setAccount(account);
        user.setPasswd(password);
        user.setEmail(email);
        user.setCellphone(account);
        if (userService.insertSelective(user) != 1) {
            return new Result(CodeEnums.EXCEPTION.getCode(), CodeEnums.EXCEPTION.getMessage());
        }
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(user.getUserId());
        if (roleId != null) {
            sysUserRole.setRoleId(roleId);
        } else {
            sysUserRole.setRoleId(3L);
        }
        systemService.insertSelective(sysUserRole);
        return Result.SUCCESS();
    }


    @ApiOperation("用户登录")
    @ApiImplicitParam(name = "param", value = "account:必选:String----password:必选:String", required = true, dataType = "String", paramType = "body")
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object login(@RequestBody JSONObject param) {
        logger.info("用户登录");
        String account = param.getString("account");
        String password = param.getString("password");
        try {
            password = CryptUtil.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (account == null || password == null) {
            throw new GalaxyException(CodeEnums.MISS_INFO.getCode(), CodeEnums.MISS_INFO.getMessage());
        }
        User user = userService.selectByAccountAndPasswd(account, password);
        if (user == null) {
            return new Result(CodeEnums.ERROR_PASSWORD.getCode(), CodeEnums.ERROR_PASSWORD.getMessage());
        } else {
            String token = JWTUtil.sign(user.getAccount(), user.getPasswd(), user.getUserId());
            JSONObject data = (JSONObject) JSON.toJSON(user);
            data.put("token", token);
            redisService.hset("token", user.getUserId().toString(), token);
            return new Result(CodeEnums.SUCCESS.getCode(), CodeEnums.SUCCESS.getMessage(), data);
        }
    }

    @ApiOperation("退出登录")
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object doLogout(HttpServletRequest httpServletRequest) {
        Long userId = JWTUtil.getUserId(httpServletRequest.getHeader("Authorization"));
        if (redisService.hget("token", userId.toString()) != null) {
            redisService.hdel("token",userId.toString());
            return Result.SUCCESS();
        }
        return new Result(CodeEnums.NOT_LOGIN.getCode(), CodeEnums.NOT_LOGIN.getMessage());
    }


    @ApiOperation("上传头像")
    @ApiImplicitParam(name = "param", value = "file:必选:form", required = true, dataType = "String", paramType = "form")
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object uploadImg(@RequestParam(value = "avatar") MultipartFile file, HttpServletRequest httpServletRequest) {
        Long userId = JWTUtil.getUserId(httpServletRequest.getHeader("Authorization"));
        try {
            String avatar = FileUtil.uploadFile(file, userId.toString());
            User user = new User();
            user.setUserId(userId);
            user.setAvatar(avatar);
            userService.updateSelective(user);
            return new Result(CodeEnums.SUCCESS.getCode(), CodeEnums.SUCCESS.getMessage(), avatar);
        } catch (IOException e) {
            return new Result(CodeEnums.UPLOAD_ERROR.getCode(), CodeEnums.UPLOAD_ERROR.getMessage());
        }
    }

    @ApiOperation("更新用户信息")
    @ApiImplicitParam(name = "param", value = "name:必选:String----email:必选:String----password:可选:String----cellphone:可选:String", required = true, dataType = "String", paramType = "body")
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object updateInfo(@RequestBody JSONObject params, HttpServletRequest httpServletRequest) {
        Long userId = JWTUtil.getUserId(httpServletRequest.getHeader("Authorization"));
        User user = new User();
        String name = params.getString("name");
        String password = params.getString("password");
        String cellphone = params.getString("cellphone");
        String email = params.getString("email");
        if (name == null || email == null) {
            throw new GalaxyException(CodeEnums.MISS_INFO.getCode(), CodeEnums.MISS_INFO.getMessage());
        }
        if (password != null) {
            try {
                password = CryptUtil.encrypt(password);
            } catch (Exception e) {
                throw new GalaxyException(CodeEnums.PASSWROD_CRYPT_ERROR.getCode(), CodeEnums.PASSWROD_CRYPT_ERROR.getMessage());
            }
        }
        user.setUserId(userId);
        user.setName(name);
        user.setPasswd(password);
        user.setCellphone(cellphone);
        user.setEmail(email);
        userService.updateSelective(user);
        return new Result(CodeEnums.SUCCESS.getCode(), CodeEnums.SUCCESS.getMessage());
    }

    @ApiOperation("用户信息")
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object getUserInfo(HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);
        JSONObject data = new JSONObject();
        List<String> roles = new ArrayList<>();
        systemService.selectRoleByUserId(userId).forEach(item -> {
            roles.add(item.getRoleName());
        });
        data.put("roles", roles);
        data.put("user", userService.selectByPrimaryKey(userId));
        return new Result(CodeEnums.SUCCESS.getCode(), CodeEnums.SUCCESS.getMessage(), data);
    }
}
