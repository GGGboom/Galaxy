package com.example.Galaxy.aop;


import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.example.Galaxy.entity.OperationLog;
import com.example.Galaxy.service.OperationLogService;
import com.example.Galaxy.util.IPUtil;
import com.example.Galaxy.util.JWTUtil;
import com.example.Galaxy.util.annotation.LogAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LogAspect {

    @Autowired
    private OperationLogService operationLogService;

    //得到请求的数据信息
    private static ServletRequestAttributes attribute;

    @Pointcut("@annotation(com.example.Galaxy.util.annotation.LogAnnotation)")
    public void operationLog() {
    }

    @AfterReturning("operationLog()")
    public void doAfterReturning(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperationLog operationLog = new OperationLog();
        attribute =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attribute.getRequest();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        Method method = signature.getMethod();
        Map<String, String> paramMap = convertMap(request.getParameterMap());
        String paramStr = JSON.toJSONString(paramMap);
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);

        if (request.getHeader("Authorization") != null) {
            String token = JWT.decode(request.getHeader("Authorization")).getToken();
            Long userId = JWTUtil.getUserId(token);
            operationLog.setUserId(userId);
        } else {
            operationLog.setUserId(-1L);
        }
        if (logAnnotation != null) {
            operationLog.setDescription(logAnnotation.description());
            operationLog.setOperationType(logAnnotation.operationType().getOperationType());
        }
        operationLog.setIp(IPUtil.getIpAddr(request));
        operationLog.setUrl(request.getRequestURI());
        operationLog.setCreateTime(new Date());
        operationLog.setUrlArgs(paramStr);
        operationLog.setMethod(className + "." + methodName + "()");
        operationLogService.addOperationLog(operationLog);
    }


    @AfterThrowing("operationLog()")
    public void doAfterThrowing(JoinPoint joinPoint) {

    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> convertMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }
}
