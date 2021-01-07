package com.example.Galaxy.service;

import com.example.Galaxy.entity.SysPrivilege;
import com.example.Galaxy.entity.SysRole;
import com.example.Galaxy.entity.SysUserRole;
import com.example.Galaxy.entity.User;

import java.util.List;

public interface SystemService {
    User selectRoleAndPrivilegeByUserId(Long userId);

    List<User> selectAllUserWithRoles();

    List<SysPrivilege> selectPrivilegeByRoleId(Long roleId);

    List<SysRole> selectRoleByUserId(Long userId);

    List<SysRole> selectAllRoleWIthPrivilege();

    int updateRoleIdByUserId(SysUserRole sysUserRole);

    int insertSelective(SysUserRole sysUserRole);
}
