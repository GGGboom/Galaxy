package com.example.Galaxy.exception;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

//@RestControllerAdvice
public class OverallExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {
        CodeEnums codeEnums = CodeEnums.EXCEPTION;
        if (e instanceof GalaxyException) {
            return proccess(httpServletRequest, httpServletResponse, ((GalaxyException) e).getCode(), ((GalaxyException) e).getMessage());
        }else if (e instanceof UnauthenticatedException){
            return proccess(httpServletRequest,httpServletResponse,CodeEnums.NOT_LOGIN.getCode(),CodeEnums.NOT_LOGIN.getMessage());
        }
        return proccess(httpServletRequest,httpServletResponse,codeEnums.getCode(),codeEnums.getMessage());
    }

    private ModelAndView proccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Integer code, String msg) {
        ModelAndView empty = new ModelAndView();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("message",msg);
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setStatus(500);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = httpServletResponse.getWriter();
            writer.write(jsonObject.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }
        empty.clear();
        return empty;
    }
//    @ResponseBody
//    @ExceptionHandler(value = Exception.class)
//    public Object handleException(Exception e, HttpServletRequest request){
//        Map<String,Object>map = new HashMap<>();
//        if(e instanceof GalaxyException){
//            map.put("code",((GalaxyException)e).getCode());
//            map.put("message",((GalaxyException)e).getMessage());
//        }else{
//            map.put("code", 100);
//            map.put("msg", e.getMessage());
//        }
//        return map;
//    }
}
