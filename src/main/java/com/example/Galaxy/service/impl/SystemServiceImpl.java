package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.SysPrivilegeMapper;
import com.example.Galaxy.dao.SysRoleMapper;
import com.example.Galaxy.dao.UserMapper;
import com.example.Galaxy.entity.SysPrivilege;
import com.example.Galaxy.entity.SysRole;
import com.example.Galaxy.entity.User;
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
    private RedisTemplate<Object, Object> redisTemplate;

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
    public List<SysRole> selectRoleByUserId(Long userId) {
        List<SysRole> list = (List<SysRole>) redisTemplate.opsForHash().get(this.getClass().getSimpleName(),"selcetRole");
        if (list==null){
            list = sysRoleMapper.selectRoleAndPrivilegeByUserId(userId);
            redisTemplate.opsForHash().put(this.getClass().getSimpleName(),"selcetRole",list);
        }
        return list;
    }

    @Override
    public User selectRoleAndPrivilegeByUserId(Long userId) {
        User user = (User)redisTemplate.opsForHash().get(this.getClass().getSimpleName(),"selectRoleAndPrivilegeByUserId");
        if(user==null){
            user = userMapper.selectUserRoleAndPrivilege(userId);
            redisTemplate.opsForHash().put(this.getClass().getSimpleName(),"selectRoleAndPrivilegeByUserId",user);
        }
        return user;
    }
}
