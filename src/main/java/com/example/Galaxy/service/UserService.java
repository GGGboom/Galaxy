package com.example.Galaxy.service;

import com.example.Galaxy.entity.User;

public interface UserService {
    int register(User user);

    User selectByUserId(String userId);

    User login(String account,String passWd);

    int updateSelective(User user);
}
