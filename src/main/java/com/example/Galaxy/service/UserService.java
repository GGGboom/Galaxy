package com.example.Galaxy.service;

import com.example.Galaxy.entity.User;

public interface UserService {
    int insertSelective(User user);

    User selectByUserId(String userId);

    User selectByAccount(String account);

    User selectByAccountAndPasswd(String account,String passWd);

    int updateSelective(User user);
}
