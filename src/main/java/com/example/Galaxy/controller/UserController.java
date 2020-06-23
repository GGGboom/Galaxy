package com.example.Galaxy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.Galaxy.entity.User;
import com.example.Galaxy.service.UserService;
import com.example.Galaxy.service.authorization.TokenService;
import com.example.Galaxy.util.FileUtil;
import com.example.Galaxy.util.Result;
import com.example.Galaxy.util.exception.CodeEnums;
import com.example.Galaxy.util.exception.GalaxyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;


@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;


    /**
     * showdoc
     * @param name              必选 String 名字
     * @param account           必选 String 账户
     * @param passwd            必选 String  密码
     * @return                  {"code":0,message:"注册成功"}
     * @catalog                 用户
     * @title
     * @description             注册用户
     * @method                  post
     * @url                     localhost:8080/user/register
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public Object addUser(@RequestBody JSONObject param){
        String name = param.getString("name");
        String account = param.getString("account");
        String passwd = param.getString("passwd");
        if(name==null || account==null || passwd==null){
            return new Result(CodeEnums.MISS_INFO.getCode(),CodeEnums.MISS_INFO.getMessage()).getJsonRes();
        }
        User user = new User();
        user.setName(name);
        user.setAccount(account);
        user.setPasswd(passwd);
        if(userService.register(user)!=1){
            return new Result(CodeEnums.EXCEPTION.getCode(),CodeEnums.EXCEPTION.getMessage()).getJsonRes();
        }
        return Result.SUCCESS();
    }


    /**
     * showdoc
     * @param name              必选 String 名字
     * @param cellphone         必选 String 手机
     * @param passwd            必选 String  密码
     * @return                  {"code":0,message:"登录成功",data:{}}
     * @catalog                 用户
     * @title
     * @description             注册用户
     * @method                  post
     * @url                     localhost:8080/user/register
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public Object login(@RequestBody JSONObject param){
        String account = param.getString("account");
        String passWd = param.getString("passwd");
        if(account==null || passWd==null){
            throw new GalaxyException(CodeEnums.MISS_INFO.getCode(),CodeEnums.MISS_INFO.getMessage());
        }
        User user = userService.login(account,passWd);
        if (user==null){
            return new Result(CodeEnums.ERROR_PASSWORD.getCode(),CodeEnums.ERROR_PASSWORD.getMessage()).getJsonRes();
        }else{
            String token = tokenService.getToken(user);
            JSONObject data = JSON.parseObject(user.toString());
            data.put("token",token);
            return new Result(CodeEnums.SUCCESS.getCode(),CodeEnums.SUCCESS.getMessage(),data).getJsonRes();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public Object uploadImg(@RequestParam(value = "file") MultipartFile file){
        String headImgPath = null;
        try {
            headImgPath = FileUtil.uploadFile(file);
            return Result.SUCCESS();
        }catch (IOException e){
            e.printStackTrace();
            return new Result(CodeEnums.UPLOAD_ERROR.getCode(),CodeEnums.UPLOAD_ERROR.getMessage()).getJsonRes();
        }
    }
}
