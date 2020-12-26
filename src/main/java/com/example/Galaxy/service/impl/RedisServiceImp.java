package com.example.Galaxy.service.impl;

import com.example.Galaxy.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
public class RedisServiceImp implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean deleteCacheByMethodNames(String cacheName, String... methodNames) {
        try {
            for (String method : methodNames) {
                redisTemplate.opsForHash().delete(cacheName, method);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCacheByClass(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        String className = clazz.getSimpleName();
        className = className.substring(0, clazz.getSimpleName().indexOf("Impl") + 4);
        System.out.println(className);
        try {
            for (Method method : methods) {
                if (method.getName().startsWith("select")) {
                    System.out.println("delete" + className + "," + method.getName());
                    redisTemplate.opsForHash().delete(className, method.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
