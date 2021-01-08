package com.example.Galaxy.service;

import com.example.Galaxy.entity.User;

import java.util.List;

public interface UserService {
    int insertSelective(User user);

    User selectByPrimaryKey(Long userId);

    User selectByAccount(String account);

    User selectByAccountAndPasswd(String account,String passWd);

    int updateSelective(User user);
}
