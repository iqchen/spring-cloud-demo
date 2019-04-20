package com.spring.cloud.eureka.client.config;

import com.spring.cloud.eureka.client.constant.CacheConstants;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;

/**
 * redis config
 * {
 *     cache expire rules,
 *     key generator
 * }
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Bean("redisCacheManager")
    public CacheManager cacheManager(@Autowired RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration defaultRedisCacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(2));
        HashMap<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put(CacheConstants.EXPIRE_IN_30M,
                getRedisCacheConfiguration(CacheConstants.EXPIRE_IN_30M, DateUtils.MILLIS_PER_MINUTE * 30));
        redisCacheConfigurationMap.put(CacheConstants.EXPIRE_IN_1H,
                getRedisCacheConfiguration(CacheConstants.EXPIRE_IN_1H, DateUtils.MILLIS_PER_HOUR));
        redisCacheConfigurationMap.put(CacheConstants.EXPIRE_IN_2H,
                getRedisCacheConfiguration(CacheConstants.EXPIRE_IN_2H, DateUtils.MILLIS_PER_HOUR * 2));
        redisCacheConfigurationMap.put(CacheConstants.EXPIRE_IN_5H,
                getRedisCacheConfiguration(CacheConstants.EXPIRE_IN_5H, DateUtils.MILLIS_PER_HOUR * 5));
        redisCacheConfigurationMap.put(CacheConstants.EXPIRE_IN_10H,
                getRedisCacheConfiguration(CacheConstants.EXPIRE_IN_10H, DateUtils.MILLIS_PER_HOUR * 10));
        redisCacheConfigurationMap.put(CacheConstants.EXPIRE_IN_24H,
                getRedisCacheConfiguration(CacheConstants.EXPIRE_IN_24H, DateUtils.MILLIS_PER_HOUR * 24));
        return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory),
                defaultRedisCacheConfiguration, redisCacheConfigurationMap);
    }

    private RedisCacheConfiguration getRedisCacheConfiguration(String prefixKeys,long millis) {
        return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMillis(millis))
                .prefixKeysWith(prefixKeys);
    }

    //自定义缓存key生成策略 like "30M_UserDao_getUserNameById_1"
    @Bean("keyGenerator")
    public KeyGenerator keyGenerator() {
        return new KeyGenerator(){
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                sb.append("_");
                sb.append(method.getDeclaringClass().getSimpleName());
                sb.append("_");
                sb.append(method.getName());
                for(Object obj:params){
                    sb.append("_");
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
}
