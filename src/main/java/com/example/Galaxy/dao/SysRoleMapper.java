package com.example.Galaxy.dao;

import com.example.Galaxy.entity.SysRole;

import java.util.List;

public interface SysRoleMapper {

    int insert(SysRole record);

    int insertSelective(SysRole record);
}