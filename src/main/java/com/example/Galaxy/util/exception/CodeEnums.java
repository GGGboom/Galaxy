package com.example.Galaxy.util.exception;


/**
 * 功能描述：
 *  0    ： 成功
 *  1-10 :  登录相关的异常
 *  11-20： 参数相关的异常
 *  21-30： 业务相关的异常
 *  31-40:  第三方相关异常
 *  .
 *  99   ： 系统异常
 *  100 以上:为其他异常
 * @author zhh
 * @date 2020-6-22
 */
public enum CodeEnums {
    SUCCESS(0, "请求成功"),

    NOT_LOGIN(1, "该用户未登录"),

    ERROR_PASSWORD(2, "用户名或密码错误"),

    ERROR_VERIFY_CODE(4, "手机验证码错误"),

    NO_USER(3, "用户不存在，请重新登录"),

    ERROR_PASSWORD_AUTH(5, "密码错误"),

    ERROR_SEND_VERIFY_CODE(6, "手机验证码发送失败"),

    PARAM_ERROR(11,"非法参数"),

    CODE_ERROR(12, "状态异常"),

    BUSINESS_EXCEPTION(21, "业务异常"),

    NO_DATA(22, "暂无更多数据"),

    MISS_INFO(23,"信息不完整"),

    NO_PERMISSION(24,"无权限"),

    JWT_ERROR(25, "JWT异常"),

    UPLOAD_ERROR(26, "文件上传异常"),

    EMAIL_ERROR(31, "邮件发送异常"),

    REDIS_ERROR(101,"redis异常"),

    EXCEPTION(99,"系统异常");

    private Integer code;
    private String message;

    CodeEnums(Integer code,String message){
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
