package com.example.Galaxy.util;

import com.example.Galaxy.exception.CodeEnums;

public class Result {
    private int code;
    private String message;
    private Object data;
    private final String []map = {"成功","失败"};

    public Result(){}

    public static Object SUCCESS(){
        return new Result(CodeEnums.SUCCESS.getCode(),CodeEnums.SUCCESS.getMessage());
    }

//    public Result(CodeEnums codeEnums){
//        this.code = codeEnums.getCode();
//        this.message = codeEnums.getMessage();
//    }

    public Result(int code, String message){
        this.code = code;
        this.message = message;
    }

    public Result(int code,String message,Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(Object data){
        this.setCode(CodeEnums.SUCCESS.getCode());
        this.setMessage(CodeEnums.SUCCESS.getMessage());
        this.setData(data);
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
