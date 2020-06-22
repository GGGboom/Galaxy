package com.example.Galaxy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.Galaxy.entity.User;
import com.example.Galaxy.service.UserService;
import com.example.Galaxy.util.FileUtil;
import com.example.Galaxy.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;


@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * showdoc
     * @param name              必选 String 名字
     * @param cellphone         必选 String 手机
     * @param passwd            必选 String  密码
     * @return                  {"code":1,message:"新建合同成功"}
     * @catalog                 用户
     * @title
     * @description             注册用户
     * @method                  post
     * @url                     localhost:8080/user/register
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public Object addUser(@RequestBody JSONObject param){
        JsonResponse res = new JsonResponse();
        String name = param.getString("name");
        String cellphone = param.getString("cellphone");
        String passwd = param.getString("passwd");
        User user = new User();
        user.setName(name);
        user.setCellphone(cellphone);
        user.setPasswd(passwd);
        if(userService.register(user)==1){
            res.setCode(0);
            return res.getJsonRes();
        }
        res.setCode(1);
        return  res.getJsonRes();
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public Object uploadImg(@RequestParam(value = "file") MultipartFile file){
        JsonResponse res = new JsonResponse();
        String headImgPath = null;
        try {
            headImgPath = FileUtil.uploadFile(file);
            res.setCode(0);
        }catch (IOException e){
            e.printStackTrace();
            res.setCode(1);
        }
        System.out.println(headImgPath);
        return res.getJsonRes();
    }
}
