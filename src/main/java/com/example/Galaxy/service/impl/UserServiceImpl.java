package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.UserMapper;
import com.example.Galaxy.entity.User;
import com.example.Galaxy.service.RedisCacheService;
import com.example.Galaxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisCacheService redisCacheService;

    @Override
    public int updateSelective(User user) {
        redisCacheService.deleteCacheByClass(this.getClass());
        return userMapper.updateSelective(user);
    }

    @Override
    public int insertSelective(User user) {
        return userMapper.insertSelective(user);
    }

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public User selectByAccount(String account) {
        User user = (User) redisTemplate.opsForHash().get(this.getClass().getSimpleName(), "selectByAccount:" + account);
        if (user == null) {
            user = userMapper.selectByAccount(account);
            redisTemplate.opsForHash().put(this.getClass().getSimpleName(), "selectByAccount:" + account, user);
        }
        return user;
    }

    @Override
    public User selectByUserId(Long userId) {
        User user = (User) redisTemplate.opsForHash().get(this.getClass().getSimpleName(), "selectByUserId:" + userId);
        if (user == null) {
            user = userMapper.selectById(userId);
            redisTemplate.opsForHash().put(this.getClass().getSimpleName(), "selectByUserId:" + userId, user);
        }
        return user;
    }

    @Override
    public User selectByAccountAndPasswd(String account, String passWd) {
        User user = (User) redisTemplate.opsForHash().get(this.getClass().getSimpleName(), "selectByAccountAndPasswd:" + account);
        if (user == null) {
            user = userMapper.login(account, passWd);
            redisTemplate.opsForHash().put(this.getClass().getSimpleName(), "selectByAccountAndPasswd:" + account, user);
        }
        return user;
    }
}
