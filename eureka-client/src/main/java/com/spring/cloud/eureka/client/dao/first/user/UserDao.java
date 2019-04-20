package com.spring.cloud.eureka.client.dao.first.user;

import com.spring.cloud.eureka.client.constant.CacheConstants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    //redis key like "30M_UserDao_getUserNameById_1"
    @Select("SELECT name FROM USER WHERE id = #{id}")
    @Cacheable(value = CacheConstants.EXPIRE_IN_30M, keyGenerator = "keyGenerator")
    String getUserNameById(@Param("id") Integer id);
}
