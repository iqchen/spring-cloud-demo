package com.spring.cloud.eureka.client.controller;

import com.spring.cloud.eureka.client.service.IUserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/get/{id}")
    @ResponseBody
    public String getUser(@PathVariable("id") Integer id) {
        logger.info("call get user. id={}", id);
        return userService.getUser(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lock")
    @ResponseBody
    public String lock() {
        logger.info("call redis lock.");
        return userService.lock();
    }
}
