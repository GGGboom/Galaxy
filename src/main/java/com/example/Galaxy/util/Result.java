package com.example.Galaxy.util;

import com.alibaba.fastjson.JSONObject;
import com.example.Galaxy.util.exception.CodeEnums;

public class Result {
    private int code;
    private String message;
    private JSONObject data;
    private final String []map = {"成功","失败"};

    public Result(){}

    public static JSONObject SUCCESS(){
        return new Result(CodeEnums.SUCCESS.getCode(),CodeEnums.SUCCESS.getMessage()).getJsonRes();
    }

    public Result(CodeEnums codeEnums){
        this.code = codeEnums.getCode();
        this.message = codeEnums.getMessage();
    }

    public Result(int code, String message){
        this.code = code;
        this.message = message;
    }

    public Result(int code,String message,JSONObject data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public JSONObject getJsonRes(){
        JSONObject jsonRes = new JSONObject();
        if(this.data!=null)
            jsonRes.put("data",this.data);
        return jsonRes;
    }
}
