package com.nozuch.securitydemo.common.util;

import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@SuppressWarnings({"unchecked", "rawtypes"})
public class RedisCache {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // 设置有效时间
    public Boolean expire(final String key, final Long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    // 缓存基本对象
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 缓存基本对象并设置过期时间
    public <T> void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    // 缓存list对象
    public <T> Long setCacheList(final String key, final List<T> valueList) {
        Long count =  redisTemplate.opsForList().rightPushAll(key, valueList);
        return Objects.isNull(count) ? 0 : count;
    }

    //缓存set对象
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> valueSet) {
        BoundSetOperations<String, T> setOperation = (BoundSetOperations<String, T>) redisTemplate.boundSetOps(key);
        for (T t : valueSet) {
            setOperation.add(t);
        }
        return setOperation;
    }

    //缓存map
    public <T> void setCacheMap(final String key, final Map<String, T> valueMap) {
        if (!Objects.isNull(valueMap)) {
            redisTemplate.opsForHash().putAll(key, valueMap);
        }
    }

    //往Hash中存入数据
    public <T> void setCacheMapValue(final String key, final String hashKey, final T value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    // 获取缓存的基本对象
    public <T> T getCacheObject(final String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    // 获取缓存的基本对象列表
    public <T> Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    // 获取缓存的list对象
    public <T> List<T> getCacheList(final String key) {
        return (List<T>) redisTemplate.opsForList().range(key, 0, -1);
    }

    // 获取缓存的set
    public <T> Set<T> getCacheSet(final String key) {
        return (Set<T>) redisTemplate.opsForSet().members(key);
    }

    // 获取缓存的map
    public <T> Map<Object, Object> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    // 获取hash中的数据
    public <T> T getCacheMapValue(final String key, final String hashKey) {
        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }

    // 获取多个hash中的数据
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hashKeys) {
        return (List<T>) redisTemplate.opsForHash().multiGet(key, hashKeys);
    }

    // 删除单个对象
    public Boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    // 删除集合对象
    public Long deleteObject(final Collection<String> collection) {
        return redisTemplate.delete(collection);
    }

    // 删除hash中的数据
    public Long deleteMapValue(final String key, final String hashKey) {
        return redisTemplate.opsForHash().delete(key, hashKey);
    }
}
