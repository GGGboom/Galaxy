package com.example.Galaxy.service;

import com.example.Galaxy.entity.User;

import java.util.List;

public interface SystemService {
    List<User>getUserRole();

    List<User>getRoleListByUserId(Long userId);
}
