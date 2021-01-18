package com.example.Galaxy.dao;

import com.example.Galaxy.entity.SysRolePrivilege;

public interface SysRolePrivilegeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRolePrivilege record);

    int insertSelective(SysRolePrivilege record);

    SysRolePrivilege selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRolePrivilege record);

    int updateByPrimaryKey(SysRolePrivilege record);
}