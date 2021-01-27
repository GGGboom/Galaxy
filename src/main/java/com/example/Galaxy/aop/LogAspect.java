package com.example.Galaxy.aop;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.example.Galaxy.entity.ExceptionLog;
import com.example.Galaxy.entity.OperationLog;
import com.example.Galaxy.exception.GalaxyException;
import com.example.Galaxy.service.LogService;
import com.example.Galaxy.util.IPUtil;
import com.example.Galaxy.util.JWTUtil;
import com.example.Galaxy.util.annotation.LogAnnotation;
import com.example.Galaxy.util.enums.ExceptionEnums;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogService logService;

    //得到请求的数据信息
    private static ServletRequestAttributes attribute;

    @Pointcut("@annotation(com.example.Galaxy.util.annotation.LogAnnotation)")
    public void logHandle() {}

    //后置增强
    @AfterReturning("logHandle()")
    public void doAfterReturning(JoinPoint joinPoint) {
        try {
            OperationLog operationLog = new OperationLog();
            saveLog(joinPoint,operationLog.getClass(),null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


    //抛出异常增强
    @AfterThrowing(value = "logHandle()", throwing = "exception")
    public void doAfterThrowing(JoinPoint joinPoint, Exception exception) {
        try {
            ExceptionLog exceptionLog = new ExceptionLog();
            GalaxyException e = (GalaxyException) exception;
            saveLog(joinPoint, exceptionLog.getClass(), e);
        } catch (Exception e1) {
            GalaxyException galaxyException = new GalaxyException(ExceptionEnums.EXCEPTION.getCode(), ExceptionEnums.EXCEPTION.getMessage());
            try {
                saveLog(joinPoint, galaxyException.getClass(), galaxyException);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    //根据日志类型保存日志记录
    public void saveLog(JoinPoint joinPoint, Class<?> clazz, GalaxyException e) throws IllegalAccessException, InstantiationException {
        Object object = clazz.newInstance();
        attribute =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        JSONObject json = new JSONObject();
        HttpServletRequest request = attribute.getRequest();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        Object[] args = joinPoint.getArgs();
        String[] paramsName = signature.getParameterNames();
        Method method = signature.getMethod();
        for (int i = 0; i < paramsName.length; i++) {
            if (!(args[i] instanceof HttpServletRequest))
                json.put(paramsName[i], args[i]);
        }
        String token = JWT.decode(request.getHeader("Authorization")).getToken();
        if (object instanceof OperationLog) {
            OperationLog operationLog = (OperationLog) object;
            operationLog.setUserId(JWTUtil.getUserId(token));
            operationLog.setUserName(JWTUtil.getUserName(token));
            operationLog.setIp(IPUtil.getIpAddr(request));
            operationLog.setUrl(request.getRequestURI());
            operationLog.setCreateTime(new Date());
            operationLog.setUrlArgs(json.toJSONString());
            operationLog.setMethod(className + "." + methodName + "()");
            LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
            if (logAnnotation != null) {
                operationLog.setDescription(logAnnotation.description());
                operationLog.setOperationType(logAnnotation.operationType().getOperationType());
            }
            logService.addOperationLog(operationLog);
        }else {
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setUserId(JWTUtil.getUserId(token));
            exceptionLog.setUserName(JWTUtil.getUserName(token));
            exceptionLog.setIp(IPUtil.getIpAddr(request));
            exceptionLog.setUrl(request.getRequestURI());
            exceptionLog.setCreateTime(new Date());
            exceptionLog.setUrlArgs(json.toJSONString());
            exceptionLog.setMethod(className + "." + methodName + "()");
            exceptionLog.setHttpMethod(request.getMethod());
            //处理未知错误信息
            String[] exMsg = e.getMessage().split(",");
            if (exMsg.length == 2) {
                e.setMessage(exMsg[0] + "，错误具体信息：" + exMsg[1]);
            } else {
                e.setMessage(e.getMessage());
            }
            exceptionLog.setExcMsg(e.getMessage());
            logService.addExceptionLog(exceptionLog);
        }
    }


    /**
     * 异常返回数据
     */
    private void exceptionReturn(GalaxyException e) {
        HttpServletResponse response = attribute.getResponse();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            JSONObject json = new JSONObject();
            json.put("code", e.getCode());
            json.put("message", e.getMessage());
            writer.print(json.toJSONString());
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }
}
