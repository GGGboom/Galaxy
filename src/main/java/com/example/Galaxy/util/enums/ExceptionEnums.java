package com.example.Galaxy.util.enums;


/**
 * 功能描述：
 *  0    ： 成功
 *  1-10 :  登录、注册相关的异常
 *  11-20： JWT相关的异常
 *  21-30： 业务相关的异常
 *  31-40:  shiro相关异常
 *  41-50:  其他异常
 *  99   ： 系统异常
 * @author zhh
 * @date 2020-6-22
 */
public enum ExceptionEnums {
    SUCCESS(0, "请求成功"),

    NOT_LOGIN(1, "未登录"),

    ERROR_PASSWORD(2, "用户名或密码错误"),

    PASSWORD_CRYPT_ERROR(3, "密码加密错误"),

    LOGIN_TIMEOUT(4,"登录过期"),

    ACCOUNT_DUPLICATE_ERROR(5,"对不起，该账号已存在"),

    ERROR_SIGN_TOKEN(11, "JWT token生成失败"),

    ERROR_TOKEN_EXPIRED(12, "JWT token过期"),

    ERROR_TOKEN_INVALID(13,"JWT token无效"),

    MISS_INFO(21,"信息不完整"),

    UPLOAD_ERROR(22, "文件上传异常"),

    AUTHORITY_ERROR(31,"无权限"),

    AUTHENTICATION_ERROR(32,"认证失败"),

    NOT_FOUND(41, "无此请求"),

    EXCEPTION(99,"系统未知异常");

    private Integer code;
    private String message;

    ExceptionEnums(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
