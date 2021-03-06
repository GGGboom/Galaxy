package com.example.Galaxy.exception;

import com.example.Galaxy.util.enums.ExceptionEnums;

/**
 * 系统异常类
 */
public class GalaxyException extends RuntimeException {
    //序列化
    private static final long serialVersionUID = -3076697639889780533L;

    protected Integer code;
    protected String message;

    public GalaxyException() {
        super();
    }

    public GalaxyException(String message) {
        super(message);
    }

    public GalaxyException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public GalaxyException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public GalaxyException(ExceptionEnums exceptionEnums) {
        this(exceptionEnums.getCode(), exceptionEnums.getMessage());
    }

    public GalaxyException(Throwable cause) {
        super(cause);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
