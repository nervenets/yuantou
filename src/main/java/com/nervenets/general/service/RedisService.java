package com.nervenets.general.service;

import org.springframework.stereotype.Service;

@Service
public interface RedisService {
    Boolean setNx(String key, String value);

    Long delete(String key);

    Boolean expire(String key, int expire);

    long incrBy(String key, long offset);

    long incr(String key);

    Boolean setGlobalPair(String key, String value);

    Boolean setGlobalPair(String key, String value, int expire);

    String getGlobalPair(String key);

    long getGlobalPairExpired(String key) throws Exception;

    boolean canProceed(long userId);

    boolean canProceedCustom(String key, int expire);

    void clearRedis();

    boolean hasKey(String key);
}
