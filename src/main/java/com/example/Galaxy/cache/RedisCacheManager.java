package com.example.Galaxy.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class RedisCacheManager implements CacheManager {

    /**
     * @param cacheName 认证和授权的统一缓存名称
     * @param <K>
     * @param <V>
     * @return
     * @throws CacheException
     */
    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        System.out.println(cacheName);
        return new RedisCache<K, V>(cacheName);
    }
}
