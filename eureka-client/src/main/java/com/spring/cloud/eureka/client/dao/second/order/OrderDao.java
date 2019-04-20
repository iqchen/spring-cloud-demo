package com.spring.cloud.eureka.client.dao.second.order;

import com.spring.cloud.eureka.client.constant.CacheConstants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 *  order dao
 * @author xiang
 */
@Repository
public interface OrderDao {

    @Select("SELECT name FROM `order` WHERE id = #{id}")
    @Cacheable(value = CacheConstants.EXPIRE_IN_1H, keyGenerator = "keyGenerator")
    String getOrderNameById(@Param("id") Integer id);
}
