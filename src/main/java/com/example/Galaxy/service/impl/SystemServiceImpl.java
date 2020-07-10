package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.UserMapper;
import com.example.Galaxy.entity.User;
import com.example.Galaxy.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUserRole() {
        return userMapper.selectAllUserAndRoles();
    }

    @Override
    public List<User> getRoleListByUserId(Long userId) {
        return userMapper.selectUserRoleList(userId);
    }
}
