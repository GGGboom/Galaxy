package com.example.Galaxy.util;

import com.example.Galaxy.util.enums.ExceptionEnums;

public class Result {
    private int code;
    private String message;
    private Object data;

    public Result() {
    }

    public static Object SUCCESS() {
        return new Result(ExceptionEnums.SUCCESS.getCode(), ExceptionEnums.SUCCESS.getMessage());
    }

    public static Object SUCCESS(Object data) {
        return new Result(ExceptionEnums.SUCCESS.getCode(), ExceptionEnums.SUCCESS.getMessage(), data);
    }

    public static Object FAILURE() {
        return new Result(ExceptionEnums.EXCEPTION.getCode(), ExceptionEnums.EXCEPTION.getMessage());
    }

    public static Object FAILURE(Object data) {
        return new Result(ExceptionEnums.EXCEPTION.getCode(), ExceptionEnums.EXCEPTION.getMessage(), data);
    }

    public Result(ExceptionEnums exceptionEnums) {
        this.code = exceptionEnums.getCode();
        this.message = exceptionEnums.getMessage();
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(Object data) {
        this.setCode(ExceptionEnums.SUCCESS.getCode());
        this.setMessage(ExceptionEnums.SUCCESS.getMessage());
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
