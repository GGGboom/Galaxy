package com.example.Galaxy.service;

import com.example.Galaxy.dao.SysPrivilegeMapper;
import com.example.Galaxy.dao.SysRoleMapper;
import com.example.Galaxy.dao.SysUserMapper;
import com.example.Galaxy.dao.SysUserRoleMapper;
import com.example.Galaxy.entity.SysPrivilege;
import com.example.Galaxy.entity.SysRole;
import com.example.Galaxy.entity.SysUser;
import com.example.Galaxy.entity.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysPrivilegeMapper sysPrivilegeMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    
    public List<SysPrivilege> selectPrivilegeByRoleId(Long roleId) {
        return sysPrivilegeMapper.selectPrivilegeByRoleId(roleId);
    }

    
    public List<SysRole> selectAllRoleWIthPrivilege() {
        return sysRoleMapper.selectAllRoleWIthPrivilege();
    }

    
    public int updateRoleIdByUserId(SysUserRole sysUserRole) {
        return sysUserRoleMapper.updateRoleIdByUserId(sysUserRole);
    }

    
    public int insertSelective(SysUserRole sysUserRole) {
        return sysUserRoleMapper.insertSelective(sysUserRole);
    }

    
    public List<SysUser> selectAllUserWithRoles() {
        return sysUserMapper.selectAllUserWithRoles();
    }

    
    public List<SysRole> selectRoleByUserId(Long userId) {
        return sysRoleMapper.selectRoleWithPrivilegeByUserId(userId);
    }

    
    public SysUser selectRoleAndPrivilegeByUserId(Long userId) {
        return sysUserMapper.selectUserWithRoleAndPrivilegeByPrimaryKey(userId);
    }
}
