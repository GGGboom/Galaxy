package com.example.Galaxy.dao;

import com.example.Galaxy.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleMapper {

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    int updateRoleIdByUserId(SysUserRole sysUserRole);
}