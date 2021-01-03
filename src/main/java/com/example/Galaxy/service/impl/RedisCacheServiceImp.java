package com.example.Galaxy.service.impl;

import com.example.Galaxy.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Set;

@Service
public class RedisCacheServiceImp implements RedisCacheService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public boolean putTokenCache(String token) {
        redisTemplate.opsForHash().put("tokenCache", token, token);
        return true;
    }

    @Override
    public boolean deleteTokenCacheByToken(String token) {
        try {
            redisTemplate.opsForHash().delete("tokenCache", token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


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
        String className = clazz.getSimpleName();
        className = className.substring(0, clazz.getSimpleName().indexOf("Impl") + 4);
        System.out.println(className);
        Set keys = redisTemplate.opsForHash().keys(className);
        try {
            for (Object item : keys) {
                System.out.println("delete:" + item);
                redisTemplate.opsForHash().delete(className, item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
