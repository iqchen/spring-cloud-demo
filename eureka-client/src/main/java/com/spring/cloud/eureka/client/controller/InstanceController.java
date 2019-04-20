package com.spring.cloud.eureka.client.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstanceController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(InstanceController.class);

    @Value("${server.port}")
    private Integer port;
    @Value("${spring.application.name}")
    private String applicationName;

    @RequestMapping("/getInstance")
    public String getInstance() {
        logger.info("call eureka client getInstance. port={}", port);
        return "instance = " + applicationName + ",port = " + port;
    }
}
