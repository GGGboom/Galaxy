package com.example.Galaxy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.example.Galaxy.entity.User;
import com.example.Galaxy.service.RedisCacheService;
import com.example.Galaxy.service.SystemService;
import com.example.Galaxy.service.UserService;
import com.example.Galaxy.util.FileUtil;
import com.example.Galaxy.util.JWTUtil;
import com.example.Galaxy.util.Result;
import com.example.Galaxy.exception.CodeEnums;
import com.example.Galaxy.exception.GalaxyException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private RedisCacheService redisCacheService;


    /**
     * showdoc
     *
     * @param name     必选 String 名字
     * @param account  必选 String 账户
     * @param password 必选 String  密码
     * @return {"code":0,message:"",data:{}}
     * @catalog 用户
     * @title
     * @description 注册用户
     * @method post
     * @url localhost:8080/user/register
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object addUser(@RequestBody JSONObject param) {
        String name = param.getString("name");
        String account = param.getString("account");
        String password = param.getString("password");
        if (name == null || account == null || password == null) {
            return new Result(CodeEnums.MISS_INFO.getCode(), CodeEnums.MISS_INFO.getMessage());
        }
        User user = new User();
        user.setName(name);
        user.setAccount(account);
        user.setPasswd(password);
        if (userService.insertSelective(user) != 1) {
            return new Result(CodeEnums.EXCEPTION.getCode(), CodeEnums.EXCEPTION.getMessage());
        }
        return Result.SUCCESS();
    }


    /**
     * showdoc
     *
     * @param account  必选 String 账号
     * @param password 必选 String  密码
     * @return {"code":0,message:"",data:{}}
     * @catalog 用户
     * @title
     * @description 登录
     * @method post
     * @url localhost:8080/user/login
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object login(@RequestBody JSONObject param) {
        logger.info("用户登录");
        String account = param.getString("account");
        String password = param.getString("password");
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
            redisCacheService.putTokenCache(token);
            return new Result(CodeEnums.SUCCESS.getCode(), CodeEnums.SUCCESS.getMessage(), data);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object doLogout(HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        redisCacheService.deleteTokenCacheByToken(token);
        return Result.SUCCESS();
    }

    /**
     * showdoc
     *
     * @param file 必选 file 文件
     * @return {"code":0,message:"",data:{}}
     * @catalog 用户
     * @title
     * @description 上传头像
     * @method post
     * @url localhost:8080/user/upload
     */
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object uploadImg(@RequestParam(value = "file") MultipartFile file, HttpServletRequest httpServletRequest) {
        String headImgPath = null;
        Long userId = JWTUtil.getUserId(httpServletRequest.getHeader("Authorization"));
        try {
            headImgPath = FileUtil.uploadFile(file);
            User user = new User();
            user.setUserId(userId);
            user.setHeadImgPath(headImgPath);
            userService.updateSelective(user);
            return Result.SUCCESS();
        } catch (IOException e) {
            return new Result(CodeEnums.UPLOAD_ERROR.getCode(), CodeEnums.UPLOAD_ERROR.getMessage());
        }
    }

    /**
     * showdoc
     *
     * @param name      必选 String  名字
     * @param email     必选 String  邮箱
     * @param password  可选 String  密码
     * @param cellphone 可选 String  手机号码
     * @return {"code":0,message:"",data:{}}
     * @catalog 用户
     * @title
     * @description 登录
     * @method post
     * @url localhost:8080/user/login
     */
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
        user.setUserId(userId);
        user.setName(name);
        user.setPasswd(password);
        user.setCellphone(cellphone);
        user.setEmail(email);
        userService.updateSelective(user);
        return new Result(CodeEnums.SUCCESS.getCode(), CodeEnums.SUCCESS.getMessage());
    }

    /**
     * showdoc
     *
     * @return {"code":0,message:"",data:{}}
     * @catalog 用户
     * @title
     * @description 登录
     * @method post
     * @url localhost:8080/user/info
     */
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
        data.put("user", userService.selectByUserId(userId));
        return new Result(CodeEnums.SUCCESS.getCode(), CodeEnums.SUCCESS.getMessage(), data);
    }
}
