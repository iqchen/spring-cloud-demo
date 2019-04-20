package com.spring.cloud.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@FeignClient(value = "eureka-service", fallback = DefaultInstanceClient.class)
public interface InstanceClient {

    @RequestMapping("/getInstance")
    String getInstance();
}
