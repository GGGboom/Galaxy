package com.example.Galaxy.dao;

import com.example.Galaxy.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    User selectById(String userId);

    User login(@Param("account")String account,@Param("passWd") String passWd);

    int updateSelective(User user);


    List<User>selectAllUserAndRoles();

    List<User>selectUserRoleList(Long userId);
}