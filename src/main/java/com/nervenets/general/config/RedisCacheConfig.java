package com.nervenets.general.config;

import com.alibaba.fastjson.JSON;
import com.nervenets.general.entity.RedisCacheableObject;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis缓存机制配置，默认过期时间30天原则
 */
@Configuration
public class RedisCacheConfig {
    @Bean
    public KeyGenerator simpleKeyGenerator() {
        return (o, method, objects) -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(o.getClass().getSimpleName());
            stringBuilder.append(".");
            stringBuilder.append(method.getName());
            stringBuilder.append("[");
            for (Object obj : objects) {
                stringBuilder.append(obj.toString());
            }
            stringBuilder.append("]");

            return stringBuilder.toString();
        };
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                this.getRedisCacheConfigurationWithTtl(30 * 24 * 60 * 60), // 默认策略30天，未配置的 key 会使用这个
                this.getRedisCacheConfigurationMap() // 指定 key 策略
        );
    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put("10s", this.getRedisCacheConfigurationWithTtl(10));
        redisCacheConfigurationMap.put("30s", this.getRedisCacheConfigurationWithTtl(30));
        redisCacheConfigurationMap.put("60s", this.getRedisCacheConfigurationWithTtl(60));
        redisCacheConfigurationMap.put("120s", this.getRedisCacheConfigurationWithTtl(120));
        redisCacheConfigurationMap.put("300s", this.getRedisCacheConfigurationWithTtl(300));
        redisCacheConfigurationMap.put("600s", this.getRedisCacheConfigurationWithTtl(600));
        redisCacheConfigurationMap.put("day1", this.getRedisCacheConfigurationWithTtl(24 * 60 * 60));
        redisCacheConfigurationMap.put("day7", this.getRedisCacheConfigurationWithTtl(7 * 24 * 60 * 60));
        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new RedisSerializer<Object>() {
                            @Override
                            public byte[] serialize(Object o) throws SerializationException {
                                boolean array = o instanceof Collection;
                                String className = o.getClass().getName();
                                if (array) {
                                    Collection o1 = (Collection) o;
                                    for (Object o2 : o1) {
                                        className = o2.getClass().getName();
                                        break;
                                    }
                                }
                                return JSON.toJSONString(new RedisCacheableObject(className, o, array)).getBytes();
                            }

                            @Override
                            public Object deserialize(byte[] bytes) throws SerializationException {
                                final RedisCacheableObject obj = JSON.parseObject(new String(bytes), RedisCacheableObject.class);
                                try {
                                    return obj.isArray() ?
                                            JSON.parseArray(obj.getValue().toString(), Class.forName(obj.getKey())) :
                                            JSON.parseObject(obj.getValue().toString(), Class.forName(obj.getKey()));
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        })
        ).entryTtl(Duration.ofSeconds(seconds));

        return redisCacheConfiguration;
    }
}
