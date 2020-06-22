package com.example.Galaxy.dao;

import com.example.Galaxy.entity.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    User selectById(Integer userId);
}