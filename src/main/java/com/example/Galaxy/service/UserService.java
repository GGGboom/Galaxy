package com.example.Galaxy.service;

import com.example.Galaxy.entity.User;

public interface UserService {
    int register(User user);
    String selectUser(Integer userId);
}
