package com.spring.cloud.eureka.client.util;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.util.Assert;

/**
 * redis utils
 * @link http://www.redis.cn/topics/distlock.html
 */
public class RedisUtils {

    /**
     * 尝试获取分布式锁
     * @param connectionFactory Redis connection
     * @param lockKey 锁
     * @param requestId 请求标识，标识当前机器，实现本机器才能释放该锁，可以是UUID
     * @param expireTime 超期时间(seconds)
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(RedisConnectionFactory connectionFactory, String lockKey, String requestId, int expireTime) {
        Assert.notNull(connectionFactory, "RedisConnectionFactory must not be null!");
        Assert.notNull(lockKey, "lockKey must not be null!");
        Assert.notNull(requestId, "requestId must not be null!");

        if (expireTime <= 0) {
            expireTime = 10;
        }
        Boolean result = connectionFactory.getConnection().set(lockKey.getBytes(), requestId.getBytes(),
                Expiration.seconds(expireTime), RedisStringCommands.SetOption.ifAbsent());
        return result != null && result;
    }

    /**
     * 释放分布式锁
     * @param connectionFactory Redis connection
     * @param lockKey 锁键
     * @param requestId 请求标识，标识当前机器，实现本机器才能释放该锁，可以是UUID
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(RedisConnectionFactory connectionFactory, String lockKey, String requestId) {
        Assert.notNull(connectionFactory, "RedisConnectionFactory must not be null!");
        Assert.notNull(lockKey, "lockKey must not be null!");
        Assert.notNull(requestId, "requestId must not be null!");

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        byte[][] keys = new byte[][]{lockKey.getBytes(), requestId.getBytes()};
        Object result = connectionFactory.getConnection().eval(script.getBytes(), ReturnType.BOOLEAN, 1, keys);
        return (boolean) result;

    }
}
