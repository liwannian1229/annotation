package com.lwn.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void setEx(String key, Object value, long expireTime) {

        try {
            ValueOperations ops = redisTemplate.opsForValue();
            ops.set(key, value);
            if (expireTime != -1) {

                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean exists(String key) {

        return redisTemplate.hasKey(key);
    }

    public boolean remove(String key) {

        if (exists(key)) {

            return redisTemplate.delete(key);
        }

        return false;
    }
}
