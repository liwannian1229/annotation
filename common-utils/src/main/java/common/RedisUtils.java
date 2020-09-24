package common;

import com.google.gson.Gson;
import cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisUtils implements CacheService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ValueOperations<String, String> valueOperations;

    @Autowired
    private HashOperations<String, String, String> hashOperations;

    @Autowired
    private ListOperations<String, String> listOperations;

    @Autowired
    private SetOperations<String, String> setOperations;

    @Autowired
    private ZSetOperations<String, String> zSetOperations;
    /**
     * 默认过期时长为12小时，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 12;
    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1;

    private final static Gson GSON = new Gson();

    @Value("${my-annotation.key-prefix}")
    private String prefix;

    public RedisUtils() {
    }

    public String prefix(String key) {

        return CommonUtil.isEmpty(prefix) ? key : prefix + "_" + key;
    }

    @Override
    public void set(String key, Object value, long expire) {
        try {
            valueOperations.set(key, toJson(value));
            if (expire != NOT_EXPIRE) {
                // 重新设置过期时间为expire,也就是刷新时间
                redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    @Override
    public <T> T get(String key, Class<T> clazz, long expire) {
        try {
            String value = valueOperations.get(key);
            if (expire != NOT_EXPIRE) {
                redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            }
            return value == null ? null : fromJson(value, clazz);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }


    public String get(String key, long expire) {
        try {
            String value = valueOperations.get(key);
            if (expire != NOT_EXPIRE) {
                redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            }

            return value;
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return null;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    @Override
    public void delete(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void deleteKeys(String key) {
        try {
            Set<String> keys = redisTemplate.keys(key + "*");
            assert keys != null;
            redisTemplate.delete(keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean exists(String key) {

        return redisTemplate.hasKey(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return GSON.toJson(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }
}
