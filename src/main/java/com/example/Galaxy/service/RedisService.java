package com.example.Galaxy.service;

public interface RedisService {
    boolean deleteCacheByMethodNames(String cacheName, String... methodName);

    boolean deleteCacheByClass(Class<?>clazz);
}
