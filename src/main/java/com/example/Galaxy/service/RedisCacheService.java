package com.example.Galaxy.service;

public interface RedisCacheService {
    boolean deleteCacheByMethodNames(String cacheName, String... methodName);

    boolean deleteCacheByClass(Class<?>clazz);

    boolean deleteTokenCacheByToken(String token);

    boolean putTokenCache(String token);
}
