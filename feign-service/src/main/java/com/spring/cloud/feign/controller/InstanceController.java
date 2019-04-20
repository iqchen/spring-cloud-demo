package com.spring.cloud.feign.controller;

import com.spring.cloud.feign.client.InstanceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class InstanceController {

    private static final Logger logger = LoggerFactory.getLogger(InstanceController.class);
    @Autowired
    private InstanceClient instanceClient;

    @RequestMapping(value = "getInstance")
    public String getInstance() {
        logger.info("call feign getInstance");
        return instanceClient.getInstance();
    }
}
