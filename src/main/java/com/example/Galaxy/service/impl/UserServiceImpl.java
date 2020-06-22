package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.UserMapper;
import com.example.Galaxy.entity.User;
import com.example.Galaxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int register(User user){
        return userMapper.insertSelective(user);
    }

    @Override
    public User selectByUserId(String userId){
        return userMapper.selectById(userId);
    }

    @Override
    public User login(String account,String passWd){
        return userMapper.login(account,passWd);
    }
}
