package com.example.Galaxy.dao;

import com.example.Galaxy.entity.SysPrivilege;

import java.util.List;

public interface SysPrivilegeMapper {
    int deleteByPrimaryKey(Long privilegeId);

    int insert(SysPrivilege record);

    int insertSelective(SysPrivilege record);

    SysPrivilege selectByPrimaryKey(Long privilegeId);

    int updateByPrimaryKeySelective(SysPrivilege record);

    int updateByPrimaryKey(SysPrivilege record);

    List<com.example.Galaxy.entity.SysPrivilege> selectPrivilegeByRoleId(Long roleId);
}