package com.example.Galaxy.dao;

import com.example.Galaxy.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKeyWithBLOBs(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectByAccount(String account);

    SysUser login(String account, String passWd);

    List<SysUser> selectAllUserWithRoles();

    SysUser selectUserWithRoleAndPrivilegeByPrimaryKey(Long userId);
}