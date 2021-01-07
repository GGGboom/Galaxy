package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.SysPrivilegeMapper;
import com.example.Galaxy.dao.SysRoleMapper;
import com.example.Galaxy.dao.SysUserRoleMapper;
import com.example.Galaxy.dao.UserMapper;
import com.example.Galaxy.entity.SysPrivilege;
import com.example.Galaxy.entity.SysRole;
import com.example.Galaxy.entity.SysUserRole;
import com.example.Galaxy.entity.User;
import com.example.Galaxy.service.RedisCacheService;
import com.example.Galaxy.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedisCacheService redisCacheService;

    @Override
    public List<SysPrivilege> selectPrivilegeByRoleId(Long roleId) {
        List<SysPrivilege>list = (List<SysPrivilege>) redisTemplate.opsForHash().get(this.getClass().getSimpleName(),"selectPrivilege");
        if(list==null){
            list = sysPrivilegeMapper.selectPrivilegeByRoleId(roleId);
            redisTemplate.opsForHash().put(this.getClass().getSimpleName(),"selectPrivilege",list);
        }
        return list;
    }

    @Override
    public List<SysRole> selectAllRoleWIthPrivilege() {
        return sysRoleMapper.selectAllRoleWIthPrivilege();
    }

    @Override
    public int updateRoleIdByUserId(SysUserRole sysUserRole) {
        redisCacheService.deleteCacheByClass(this.getClass());
        return sysUserRoleMapper.updateRoleIdByUserId(sysUserRole);
    }

    @Override
    public int insertSelective(SysUserRole sysUserRole) {
        redisCacheService.deleteCacheByClass(this.getClass());
        return sysUserRoleMapper.insertSelective(sysUserRole);
    }

    @Override
    public List<User> selectAllUserWithRoles() {
        return userMapper.selectAllUserWithRoles();
    }

    @Override
    public List<SysRole> selectRoleByUserId(Long userId) {
        List<SysRole> list = (List<SysRole>) redisTemplate.opsForHash().get(this.getClass().getSimpleName(),"selcetRole");
        if (list==null){
            list = sysRoleMapper.selectRoleWithPrivilegeByUserId(userId);
            redisTemplate.opsForHash().put(this.getClass().getSimpleName(),"selcetRole",list);
        }
        return list;
    }

    @Override
    public User selectRoleAndPrivilegeByUserId(Long userId) {
        User user = (User)redisTemplate.opsForHash().get(this.getClass().getSimpleName(),"selectUserWithRoleAndPrivilegeByUserId");
        if(user==null){
            user = userMapper.selectUserWithRoleAndPrivilegeByUserId(userId);
            redisTemplate.opsForHash().put(this.getClass().getSimpleName(),"selectUserWithRoleAndPrivilegeByUserId",user);
        }
        return user;
    }
}
