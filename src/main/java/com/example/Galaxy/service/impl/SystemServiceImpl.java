package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.SysPrivilegeMapper;
import com.example.Galaxy.dao.SysRoleMapper;
import com.example.Galaxy.dao.SysUserRoleMapper;
import com.example.Galaxy.dao.UserMapper;
import com.example.Galaxy.entity.SysPrivilege;
import com.example.Galaxy.entity.SysRole;
import com.example.Galaxy.entity.SysUserRole;
import com.example.Galaxy.entity.User;
import com.example.Galaxy.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysPrivilegeMapper sysPrivilegeMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysPrivilege> selectPrivilegeByRoleId(Long roleId) {
        return sysPrivilegeMapper.selectPrivilegeByRoleId(roleId);
    }

    @Override
    public List<SysRole> selectAllRoleWIthPrivilege() {
        return sysRoleMapper.selectAllRoleWIthPrivilege();
    }

    @Override
    public int updateRoleIdByUserId(SysUserRole sysUserRole) {
        return sysUserRoleMapper.updateRoleIdByUserId(sysUserRole);
    }

    @Override
    public int insertSelective(SysUserRole sysUserRole) {
        return sysUserRoleMapper.insertSelective(sysUserRole);
    }

    @Override
    public List<User> selectAllUserWithRoles() {
        return userMapper.selectAllUserWithRoles();
    }

    @Override
    public List<SysRole> selectRoleByUserId(Long userId) {
        return sysRoleMapper.selectRoleWithPrivilegeByUserId(userId);
    }

    @Override
    public User selectRoleAndPrivilegeByUserId(Long userId) {
        return userMapper.selectUserWithRoleAndPrivilegeByPrimaryKey(userId);
    }
}
