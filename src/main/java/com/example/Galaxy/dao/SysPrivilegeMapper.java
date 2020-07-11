package com.example.Galaxy.dao;

import com.example.Galaxy.entity.SysPrivilege;

import java.util.List;

public interface SysPrivilegeMapper {
    int insert(SysPrivilege record);

    int insertSelective(SysPrivilege record);

    List<SysPrivilege> selectPrivilegeByRoleId(Long roleId);
}