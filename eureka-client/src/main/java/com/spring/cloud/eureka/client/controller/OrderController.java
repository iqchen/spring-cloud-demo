package com.spring.cloud.eureka.client.controller;

import com.spring.cloud.eureka.client.service.IOrderService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private IOrderService orderService;

    @RequestMapping(method = RequestMethod.GET, value = "/get/{id}")
    @ResponseBody
    public String getOrder(@PathVariable("id") Integer id) {
        logger.info("call get order. id={}", id);
        String order = orderService.getOrder(id);
        return order;
    }
}
