package com.example.Galaxy.exception;


import com.example.Galaxy.util.JsonResult;
import com.example.Galaxy.util.enums.ExceptionEnums;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandle {
    /**
     * Log4j日志处理
     */
    private static final Logger log = Logger.getLogger(GlobalExceptionHandle.class);

    /**
     * 405 - Method Not Allowed。HttpRequestMethodNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error("request_method_not_supported...", e);
        return JsonResult.FAILURE("该API的请求方法不支持");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return JsonResult.FAILURE();
    }



    /**
     * 415 - Unsupported Media Type。HttpMediaTypeNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public Object handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("content_type_not_supported...", e);
        return JsonResult.FAILURE("content-type无效");
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        log.error("Internal Server Error...", e);
        return new JsonResult(ExceptionEnums.EXCEPTION.getCode(), ExceptionEnums.EXCEPTION.getMessage());
    }

    /**
     * 无此请求
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoHandlerFoundException.class})
    public Object urlNotFoundException(Exception e) {
        log.error("url is not found", e);
        return new JsonResult(ExceptionEnums.NOT_FOUND.getCode(), ExceptionEnums.NOT_FOUND.getMessage());
    }

    /**
     *无某些权限
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Object unauthorizedException(Exception e) {
        log.error("unauthorizedException Error...", e);
        return new JsonResult(ExceptionEnums.AUTHORITY_ERROR.getCode(), ExceptionEnums.AUTHORITY_ERROR.getMessage());
    }

    /**
     * 没有认证
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public Object unauthenticatedException(Exception e) {
        log.error("UnauthenticatedException Error...", e);
        return new JsonResult(ExceptionEnums.AUTHENTICATION_ERROR.getCode(), ExceptionEnums.AUTHENTICATION_ERROR.getMessage());
    }

    @ExceptionHandler(GalaxyException.class)
    public Object GalaxyExceptionHandle(Exception e) {
        log.error("UnauthenticatedException Error...", e);
        return new JsonResult(((GalaxyException)e).getCode(), ((GalaxyException)e).getMessage());
    }


}
