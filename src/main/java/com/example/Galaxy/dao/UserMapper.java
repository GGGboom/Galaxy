package com.example.Galaxy.dao;

import com.example.Galaxy.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    User selectById(String userId);

    User selectByAccount(String account);

    User login(@Param("account")String account,@Param("passWd") String passWd);

    int updateSelective(User user);

    User selectAllUserAndRoles(Long userId);

    User selectUserRoleAndPrivilege(Long userId);
}