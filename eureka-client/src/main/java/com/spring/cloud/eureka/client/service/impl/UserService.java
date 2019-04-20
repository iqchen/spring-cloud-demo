package com.spring.cloud.eureka.client.service.impl;

import com.spring.cloud.eureka.client.dao.first.user.UserDao;
import com.spring.cloud.eureka.client.service.IUserService;
import com.spring.cloud.eureka.client.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService implements IUserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public String getUser(Integer id) {
        return userDao.getUserNameById(id);
    }

    @Override
    public String lock() {
        String result = "获取锁失败";
        boolean lock = RedisUtils.tryGetDistributedLock(redisConnectionFactory, "user1", "12345", 30);
        if (lock) {
            //get redis lock
            //do something ...
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = "成功获取锁,释放锁失败";
            boolean release = RedisUtils.releaseDistributedLock(redisConnectionFactory, "user1", "12345");
            if (release) result = "成功获取锁并成功释放锁";
        }
        return result;
    }
}
