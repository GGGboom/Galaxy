package com.example.Galaxy.dao;

import com.example.Galaxy.entity.SysRole;

import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> selectRoleWithPrivilegeByUserId(Long userId);

    List<SysRole> selectRolesByUserId(Long userId);

    List<SysRole> selectAllRoleWIthPrivilege();
}