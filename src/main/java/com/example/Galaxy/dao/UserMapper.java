package com.example.Galaxy.dao;

import com.example.Galaxy.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    int updateByPrimaryKeySelective(User user);

    User selectByPrimaryKey(Long userId);

    User selectByAccount(String account);

    User login(@Param("account") String account, @Param("passWd") String passWd);

    List<User> selectAllUserWithRoles();

    User selectUserWithRoleAndPrivilegeByPrimaryKey(Long userId);
}