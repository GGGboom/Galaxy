package com.example.Galaxy.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    public Object handleException(Exception e, HttpServletRequest request){
        Map<String,Object>map = new HashMap<>();
        if(e instanceof GalaxyException){
            map.put("code",((GalaxyException)e).getCode());
            map.put("message",((GalaxyException)e).getMessage());
        }else{
            map.put("code", 100);
            map.put("msg", e.getMessage());
        }
        return map;
    }
}
