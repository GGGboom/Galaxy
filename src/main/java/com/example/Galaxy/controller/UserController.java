package com.example.Galaxy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.example.Galaxy.entity.SysUser;
import com.example.Galaxy.entity.SysUserRole;
import com.example.Galaxy.service.CheckService;
import com.example.Galaxy.service.RedisService;
import com.example.Galaxy.service.SystemService;
import com.example.Galaxy.service.UserService;
import com.example.Galaxy.util.*;
import com.example.Galaxy.util.annotation.LogAnnotation;
import com.example.Galaxy.util.enums.ExceptionEnums;
import com.example.Galaxy.exception.GalaxyException;
import com.example.Galaxy.util.enums.OperationType;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Api("用户管理")
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class);

    @Value("${captcha.expire-time}")
    private Integer expireTime;

    @Value("${captcha.redis-name}")
    private String redisName;

    @Autowired
    private UserService userService;

    @Autowired
    private SystemService systemService;

    @Lazy
    @Autowired
    private RedisService redisService;

    @Lazy
    @Autowired
    private CheckService checkService;


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
            return new JsonResult(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
        }
        try {
            password = CryptUtil.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!checkService.checkAccount(account)) {
            throw new GalaxyException(ExceptionEnums.ACCOUNT_DUPLICATE_ERROR.getCode(), ExceptionEnums.ACCOUNT_DUPLICATE_ERROR.getMessage());
        }
        SysUser sysUser = new SysUser();
        sysUser.setName(name);
        sysUser.setAccount(account);
        sysUser.setPasswd(password);
        sysUser.setEmail(email);
        sysUser.setCellphone(account);
        if (userService.insertSelective(sysUser) != 1) {
            return new JsonResult(ExceptionEnums.EXCEPTION.getCode(), ExceptionEnums.EXCEPTION.getMessage());
        }
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getUserId());
        if (roleId != null) {
            sysUserRole.setRoleId(roleId);
        } else {
            sysUserRole.setRoleId(3L);
        }
        systemService.insertSelective(sysUserRole);
        return JsonResult.SUCCESS();
    }


    @ApiOperation("用户登录")
    @ApiImplicitParam(name = "param", value = "account:必选:String----password:必选:String", required = true, dataType = "String", paramType = "body")
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object authenticate(@RequestBody JSONObject param) {
        logger.info("用户登录");
        String account = param.getString("account");
        String password = param.getString("password");
        try {
            password = CryptUtil.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (account == null || password == null) {
            throw new GalaxyException(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
        }
        SysUser sysUser = userService.selectByAccountAndPasswd(account, password);
        if (sysUser == null) {
            return new JsonResult(ExceptionEnums.ERROR_PASSWORD.getCode(), ExceptionEnums.ERROR_PASSWORD.getMessage());
        } else {
            String token = JWTUtil.sign(sysUser.getAccount(), sysUser.getPasswd(), sysUser.getUserId(), sysUser.getName());
            JSONObject data = null;
            try {
                data = (JSONObject) JSON.toJSON(userService.removeSensitiveData(sysUser));
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            data.put("token", token);
            redisService.hset("token", sysUser.getUserId().toString(), token);
            return JsonResult.SUCCESS(data);
        }
    }

    @ApiOperation("用户登录")
    @ApiImplicitParam(name = "param", value = "account:必选:String----password:必选:String", required = true, dataType = "String", paramType = "body")
    @ResponseBody
    @RequestMapping(value = "/loginByCaptcha", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object authenticateByCaptcha(@RequestBody JSONObject param) {
        logger.info("验证码登录");
        String account = param.getString("account");
        String password = param.getString("password");
        String captcha = param.getString("captcha");
        if (account == null || password == null) {
            throw new GalaxyException(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
        }
        if (captcha == null || captcha.equals("")) {
            throw new GalaxyException(ExceptionEnums.NEED_CAPTCHA.getCode(), ExceptionEnums.NEED_CAPTCHA.getMessage());
        }
        String hget = (String) redisService.hget(redisName, account);
        if (hget == null) {
            throw new GalaxyException(ExceptionEnums.CAPTCHA_EXPIRE.getCode(), ExceptionEnums.CAPTCHA_EXPIRE.getMessage());
        }
        if (!hget.equals(captcha)) {
            throw new GalaxyException(ExceptionEnums.ERROR_CAPTCHA.getCode(), ExceptionEnums.ERROR_CAPTCHA.getMessage());
        }
        try {
            password = CryptUtil.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SysUser sysUser = userService.selectByAccountAndPasswd(account, password);
        if (sysUser == null) {
            return new JsonResult(ExceptionEnums.ERROR_PASSWORD.getCode(), ExceptionEnums.ERROR_PASSWORD.getMessage());
        } else {
            String token = JWTUtil.sign(sysUser.getAccount(), sysUser.getPasswd(), sysUser.getUserId(), sysUser.getName());
            JSONObject data = null;
            try {
                data = (JSONObject) JSON.toJSON(userService.removeSensitiveData(sysUser));
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            data.put("token", token);
            redisService.hset("token", sysUser.getUserId().toString(), token);
            return JsonResult.SUCCESS(data);
        }
    }

    @LogAnnotation(description = "退出登录", operationType = OperationType.UNKNOWN)
    @ApiOperation("退出登录")
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object doLogout(HttpServletRequest httpServletRequest) {
        Long userId = JWTUtil.getUserId(httpServletRequest.getHeader("Authorization"));
        if (redisService.hget("token", userId.toString()) != null) {
            redisService.hdel("token", userId.toString());
            return JsonResult.SUCCESS();
        }
        return new JsonResult(ExceptionEnums.NOT_LOGIN.getCode(), ExceptionEnums.NOT_LOGIN.getMessage());
    }


    @LogAnnotation(description = "上传头像", operationType = OperationType.UPDATE)
    @ApiOperation("上传头像")
    @ApiImplicitParam(name = "param", value = "file:必选:form", required = true, dataType = "String", paramType = "form")
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object uploadImg(@RequestParam(value = "avatar") MultipartFile file, HttpServletRequest httpServletRequest) {
        Long userId = JWTUtil.getUserId(httpServletRequest.getHeader("Authorization"));
        try {
            String avatar = FileUtil.uploadFile(file, userId.toString());
            SysUser sysUser = new SysUser();
            sysUser.setUserId(userId);
            sysUser.setAvatar(avatar);
            userService.updateSelective(sysUser);
            return JsonResult.SUCCESS(avatar);
        } catch (IOException e) {
            return new JsonResult(ExceptionEnums.UPLOAD_ERROR.getCode(), ExceptionEnums.UPLOAD_ERROR.getMessage());
        }
    }

    @LogAnnotation(description = "更新用户信息", operationType = OperationType.UPDATE)
    @ApiOperation("更新用户信息")
    @ApiImplicitParam(name = "param", value = "name:必选:String----email:必选:String----password:可选:String----cellphone:可选:String", required = true, dataType = "String", paramType = "body")
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object updateInfo(@RequestBody JSONObject params, HttpServletRequest httpServletRequest) {
        Long userId = JWTUtil.getUserId(httpServletRequest.getHeader("Authorization"));
        SysUser sysUser = new SysUser();
        String name = params.getString("name");
        String password = params.getString("password");
        String cellphone = params.getString("cellphone");
        String email = params.getString("email");
        if (name == null || email == null) {
            throw new GalaxyException(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
        }
        if (password != null) {
            try {
                password = CryptUtil.encrypt(password);
            } catch (Exception e) {
                throw new GalaxyException(ExceptionEnums.PASSWORD_CRYPT_ERROR.getCode(), ExceptionEnums.PASSWORD_CRYPT_ERROR.getMessage());
            }
        }
        sysUser.setUserId(userId);
        sysUser.setName(name);
        sysUser.setPasswd(password);
        sysUser.setCellphone(cellphone);
        sysUser.setEmail(email);
        userService.updateSelective(sysUser);
        return JsonResult.SUCCESS();
    }


    @LogAnnotation(description = "用户信息", operationType = OperationType.SELECT)
    @ApiOperation("用户信息")
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object getUserInfo(HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);
        JSONObject data = new JSONObject();
        SysUser sysUser = userService.selectByPrimaryKey(userId);
        sysUser.setRoleList(systemService.selectRoleByUserId(userId));
        try {
            data.put("user", userService.removeSensitiveData(sysUser));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return JsonResult.SUCCESS(data);
    }

    @ResponseBody
    @RequestMapping(value = "/captcha", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object captcha(@RequestParam("account") String account) throws Exception {
        if (account.equals("") || account == null) {
            throw new GalaxyException(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
        }
        SysUser user = userService.selectByAccount(account);
        if (user == null) {
            throw new GalaxyException(ExceptionEnums.NOT_EXISTS_ACCOUNT.getCode(), ExceptionEnums.NOT_EXISTS_ACCOUNT.getMessage());
        }
        Captcha captcha = CaptchaUtil.generateGifCaptcha();
        String verCode = captcha.text().toLowerCase();
        // 存入redis并设置过期时间为30分钟
        redisService.hset(redisName, account, verCode, expireTime);
        System.out.println(verCode);
        // 将key和base64返回给前端
        Map<String, Object> map = new HashMap<>();
        map.put("image", captcha.toBase64());
        return JsonResult.SUCCESS(map);
    }
}
