package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.UserMapper;
import com.example.Galaxy.entity.User;
import com.example.Galaxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    @Cacheable(cacheNames = "User", key = "selectByAccount")
    public User selectByUserAccount(String account) {
        return userMapper.selectByAccount(account);
    }

    @Override
    public int register(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
//    @Cacheable(cacheNames = "User", key = "'selectById'")
    public User selectByUserId(String userId) {
        return userMapper.selectById(userId);
    }

    @Override
//    @Cacheable(cacheNames = "User", key = "'login'")
    public User login(String account, String passWd) {
        return userMapper.login(account, passWd);
    }

    @Override
    public int updateSelective(User user) {
        return userMapper.updateSelective(user);
    }
}
