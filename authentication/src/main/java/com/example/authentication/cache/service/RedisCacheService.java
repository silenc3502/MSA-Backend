package com.example.authentication.cache.service;

public interface RedisCacheService {
    <K, V> void setKeyAndValue(K key, V value);
    Long getValueByKey(String token);
    void deleteByKey(String token);
}
