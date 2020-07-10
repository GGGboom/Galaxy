package com.example.Galaxy.dao;

import com.example.Galaxy.entity.SysRolePrivilege;

import java.util.List;

public interface SysRolePrivilegeMapper {
    int insert(SysRolePrivilege record);

    int insertSelective(SysRolePrivilege record);
}