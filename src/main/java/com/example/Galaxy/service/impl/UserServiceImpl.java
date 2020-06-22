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
    public String selectUser(Integer userId){
        return userMapper.selectById(userId).toString();
    }
}
