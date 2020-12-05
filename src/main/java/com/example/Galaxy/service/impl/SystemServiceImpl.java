package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.SysPrivilegeMapper;
import com.example.Galaxy.dao.SysRoleMapper;
import com.example.Galaxy.dao.UserMapper;
import com.example.Galaxy.entity.SysPrivilege;
import com.example.Galaxy.entity.SysRole;
import com.example.Galaxy.entity.User;
import com.example.Galaxy.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Override
    public List<SysPrivilege> selectPrivilege(Long roleId) {
        return sysPrivilegeMapper.selectPrivilegeByRoleId(roleId);
    }

    @Override
    public List<SysRole> selcetRole(Long userId) {
        return sysRoleMapper.selectRoleAndPrivilegeByUserId(userId);
    }

    @Override
    @Cacheable(cacheNames = "System", key = "'selectUserRoleAndPrivilege'")
    public User selectRoleAndPrivilegeByUserId(Long userId) {
        return userMapper.selectUserRoleAndPrivilege(userId);
    }
}
