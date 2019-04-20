package com.spring.cloud.eureka.client.service.impl;

import com.spring.cloud.eureka.client.dao.second.order.OrderDao;
import com.spring.cloud.eureka.client.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderService")
public class OrderService implements IOrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public String getOrder(Integer id) {
        return orderDao.getOrderNameById(id);
    }
}
