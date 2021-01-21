package com.example.Galaxy.util;

import com.example.Galaxy.entity.Blog;
import com.example.Galaxy.entity.SysUser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class GalaxyUtil {

    public static Map mapUtil(Object o) throws InvocationTargetException, IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = o.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get")) {
                String fieldName = methodName.substring(methodName.indexOf("get") + 3);
                fieldName = fieldName.toLowerCase().charAt(0) + fieldName.substring(1);
                Object value = method.invoke(o, (Object[]) null);
                map.put(fieldName, value);
            }
        }
        return map;
    }
}
