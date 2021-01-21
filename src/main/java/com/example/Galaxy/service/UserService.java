package com.example.Galaxy.service;

import com.example.Galaxy.dao.SysUserMapper;
import com.example.Galaxy.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


@Service
public class UserService {


    @Autowired
    private SysUserMapper sysUserMapper;

    @Caching(evict = {
            @CacheEvict(value = "UserCacheSelectByAccount", allEntries = true),
            @CacheEvict(value = "UserCacheSelectByPrimaryKey", allEntries = true),
            @CacheEvict(value = "UserCacheSelectByAccountAndPasswd", allEntries = true),
    })
    public int updateSelective(SysUser sysUser) {
        return sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }


    @Caching(evict = {
            @CacheEvict(value = "UserCacheSelectByAccount", allEntries = true),
            @CacheEvict(value = "UserCacheSelectByPrimaryKey", allEntries = true),
            @CacheEvict(value = "UserCacheSelectByAccountAndPasswd", allEntries = true),
    })
    public int insertSelective(SysUser sysUser) {
        return sysUserMapper.insertSelective(sysUser);
    }


    @Cacheable(value = "UserCacheSelectByAccount", key = "#account")
    public SysUser selectByAccount(String account) {
        return sysUserMapper.selectByAccount(account);
    }


    @Cacheable(value = "UserCacheSelectByPrimaryKey", key = "#userId")
    public SysUser selectByPrimaryKey(Long userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Cacheable(value = "UserCacheSelectByAccountAndPasswd", key = "#account+#passWd")
    public SysUser selectByAccountAndPasswd(String account, String passWd) {
        return sysUserMapper.login(account, passWd);
    }

    public Map removeSensitiveData(SysUser user) throws InvocationTargetException, IllegalAccessException {
        Class<? extends SysUser> clazz = user.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        Map<String, Object> map = new HashMap<>();
        for (Method method : methods) {
            String methodName = method.getName();
            if (!methodName.equals("getPasswd") && !methodName.equals("getVerifyCode") && methodName.startsWith("get")) {
                String fieldName = methodName.substring(methodName.indexOf("get") + 3);
                fieldName = fieldName.toLowerCase().charAt(0) + fieldName.substring(1);
                Object value = method.invoke(user, (Object[]) null);
                map.put(fieldName, value);
            }
        }
        return map;
    }

}
