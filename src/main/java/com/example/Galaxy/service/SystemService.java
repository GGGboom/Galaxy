package com.example.Galaxy.service;

import com.example.Galaxy.entity.SysPrivilege;
import com.example.Galaxy.entity.SysRole;
import com.example.Galaxy.entity.User;

import java.util.List;

public interface SystemService {
    User selectRoleAndPrivilegeByUserId(Long userId);

    List<SysPrivilege> selectPrivilege(Long roleId);

    List<SysRole>selcetRole(Long userId);
}
