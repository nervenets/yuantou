package com.nervenets.general.service.impl;

import com.nervenets.general.service.RedisService;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisServiceImpl implements RedisService {
    private static final int _OPERATION_INTERVAL_SECONDS_ = 3;
    private static final String _OPERATION_CUSTOM_INTERVAL_KEY_ = "PROCEED::%s";
    @Resource
    private RedisTemplate redisTemplate;

    public Boolean setNx(String key, String value) {
        return (Boolean) redisTemplate.execute((RedisCallback) rc -> rc.setNX(key.getBytes(), value.getBytes()));
    }

    public Long delete(String key) {
        return (Long) redisTemplate.execute((RedisCallback) rc -> rc.del(key.getBytes()));
    }

    public Boolean expire(String key, int expire) {
        return (Boolean) redisTemplate.execute((RedisCallback) rc -> rc.expire(key.getBytes(), expire));
    }

    public long incrBy(String key, long offset) {
        return (long) redisTemplate.execute((RedisCallback) rc -> rc.incrBy(key.getBytes(), offset));
    }

    public long incr(String key) {
        return (long) redisTemplate.execute((RedisCallback) rc -> rc.incr(key.getBytes()));
    }

    public Boolean setGlobalPair(String key, String value) {
        return setGlobalPair(key, value, 0);
    }

    public Boolean setGlobalPair(String key, String value, int expire) {
        return (Boolean) redisTemplate.execute((RedisCallback) rc -> {
            final Boolean result = rc.set(key.getBytes(), value.getBytes());
            if (expire > 0) {
                rc.expire(key.getBytes(), expire);
            }
            return result;
        });
    }

    public String getGlobalPair(String key) {
        return (String) redisTemplate.execute((RedisCallback) rc -> new String(rc.get(key.getBytes())));
    }

    public long getGlobalPairExpired(String key) throws Exception {
        return (long) redisTemplate.execute((RedisCallback) rc -> rc.ttl(key.getBytes()));
    }

    public boolean canProceed(long userId) {
        String lockKey = String.format(_OPERATION_CUSTOM_INTERVAL_KEY_, userId);
        Boolean hasLocked = (Boolean) redisTemplate.execute((RedisCallback) rc -> rc.setNX(lockKey.getBytes(), lockKey.getBytes()));
        if (hasLocked) {
            redisTemplate.execute((RedisCallback) rc -> rc.expire(lockKey.getBytes(), _OPERATION_INTERVAL_SECONDS_));
            return true;
        } else {
            return false;
        }
    }

    public boolean canProceedCustom(String key, int expire) {
        String lockKey = String.format(_OPERATION_CUSTOM_INTERVAL_KEY_, key);
        Boolean hasLocked = (Boolean) redisTemplate.execute((RedisCallback) rc -> rc.setNX(lockKey.getBytes(), lockKey.getBytes()));
        if (hasLocked) {
            redisTemplate.execute((RedisCallback) rc -> rc.expire(lockKey.getBytes(), expire));
            return true;
        } else {
            return false;
        }
    }

    public void clearRedis() {
        redisTemplate.execute((RedisCallback) rc -> {
            rc.flushDb();
            rc.flushAll();
            return true;
        });
    }

    @Override
    public boolean hasKey(String key) {
        return (boolean) redisTemplate.execute((RedisCallback) rc -> rc.exists(key.getBytes()));
    }
}
