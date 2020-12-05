package com.example.Galaxy.cache;

import com.example.Galaxy.util.ApplicationContextUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;
import java.util.Set;

/**
 * 自定义Redis缓存的实现
 */
public class RedisCache<k, v> implements Cache<k, v> {

    private String cacheName;

    public RedisCache() {
    }

    public RedisCache(String cacheName) {
        this.cacheName = cacheName;
    }

    @Override
    public v get(k k) throws CacheException {
        System.out.println("get key:" + k);
        return (v) getTemplate().opsForHash().get(this.cacheName, k.toString());
    }

    @Override
    public v put(k k, v v) throws CacheException {
        System.out.println("put key:" + k);
        getTemplate().opsForHash().put(this.cacheName, k.toString(), v);
        return null;
    }

    @Override
    public v remove(k k) throws CacheException {
        getTemplate().opsForHash().delete(this.cacheName, k.toString());
        return null;
    }

    @Override
    public void clear() throws CacheException {
        getTemplate().delete(this.cacheName);
    }

    @Override
    public int size() {
        return getTemplate().opsForHash().size(this.cacheName).intValue();
    }

    @Override
    public Set<k> keys() {
        return getTemplate().opsForHash().keys(this.cacheName);
    }

    @Override
    public Collection<v> values() {
        return getTemplate().opsForHash().values(this.cacheName);
    }

    public RedisTemplate getTemplate() {
        RedisTemplate template = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        return template;
    }
}
