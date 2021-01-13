package com.example.Galaxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.example.Galaxy.entity.Record;
import com.example.Galaxy.util.annotation.LogAnnotation;
import com.example.Galaxy.util.enums.CodeEnums;
import com.example.Galaxy.service.RecordService;
import com.example.Galaxy.util.JWTUtil;
import com.example.Galaxy.util.Result;
import com.example.Galaxy.util.enums.OperationType;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping(value = "/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    /**
     * showdoc
     *
     * @param id  可选 Integer    用户头像
     * @param reason 必选 Integer  用户头像
     * @param amount  必选 Integer    用户头像
     * @param userId 必选 Integer  用户头像
     * @param blogId 必选 Integer  用户头像
     * @return {"code":0,message:"登录成功",data:{}}
     * @catalog record
     * @title
     * @description 插入或者更新记录
     * @method get
     * @url /record/update
     */
    @LogAnnotation(description = "插入或者更新记录",operationType = OperationType.UPDATE)
    @ResponseBody
    @RequiresRoles(value = {"admin"})
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object updateRecord(@RequestBody JSONObject params, HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long adminId = JWTUtil.getUserId(token);
        Long id = params.getLong("id");
        String reason = params.getString("reason");
        Long amount = params.getLong("amount");
        Long userId = params.getLong("userId");
        Long blogId = params.getLong("blogId");
        Date createTime = params.getDate("createTime");
        if (reason == null || amount == null || userId == null || adminId == null || blogId==null) {
            return new Result(CodeEnums.MISS_INFO.getCode(), CodeEnums.MISS_INFO.getMessage());
        }
        Record record = new Record();
        record.setReason(reason);
        record.setAmount(amount);
        record.setUpdateTime(new Date());
        record.setUserId(userId);
        record.setEditBy(adminId);
        record.setBlogId(blogId);
        if (id != null) {//如果主键非空，则为更新
            record.setId(id);
            recordService.updateByPrimaryKeySelective(record);
        } else { //如果主键为空，则为插入
            record.setCreateTime(createTime);
            recordService.insertSelective(record);
        }
        return Result.SUCCESS();
    }

    @LogAnnotation(description = "查询所有记录",operationType = OperationType.SELECT)
    @ResponseBody
    @RequiresRoles(value = {"admin"})
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object selectAllRecord(@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return new Result(CodeEnums.SUCCESS.getCode(), CodeEnums.SUCCESS.getMessage(), recordService.selectAll(pageNum, pageSize));
    }

    @LogAnnotation(description = "查询我的记录",operationType = OperationType.SELECT)
    @ResponseBody
    @RequiresUser
    @RequestMapping(value = "/mine", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object selectMine(HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);
        return new Result(CodeEnums.SUCCESS.getCode(), CodeEnums.SUCCESS.getMessage(), recordService.selectByUserId(userId));
    }

    @LogAnnotation(description = "删除我的记录",operationType = OperationType.UPDATE)
    @ResponseBody
    @RequiresRoles(value = {"admin"})
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object deleteRecord(@RequestBody JSONObject params,HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);
        return new Result(CodeEnums.SUCCESS.getCode(), CodeEnums.SUCCESS.getMessage(), recordService.deleteByPrimaryKey(userId));
    }
}
