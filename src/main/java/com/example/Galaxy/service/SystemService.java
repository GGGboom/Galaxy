package com.example.Galaxy.service;

import com.example.Galaxy.entity.SysPrivilege;
import com.example.Galaxy.entity.SysRole;
import com.example.Galaxy.entity.User;

import java.util.List;

public interface SystemService {
    User selectRoleAndPrivilegeByUserId(Long userId);

    List<SysPrivilege> selectPrivilegeByRoleId(Long roleId);

    List<SysRole> selectRoleByUserId(Long userId);
}
