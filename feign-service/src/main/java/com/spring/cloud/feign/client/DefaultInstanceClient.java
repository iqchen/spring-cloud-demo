package com.spring.cloud.feign.client;

import org.springframework.stereotype.Component;

@Component
public class DefaultInstanceClient implements InstanceClient {
    @Override
    public String getInstance() {
        return "sorry, This service has stopped";
    }
}
