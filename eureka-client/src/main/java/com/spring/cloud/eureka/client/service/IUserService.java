package com.spring.cloud.eureka.client.service;

public interface IUserService {

    String getUser(Integer id);

    String lock();
}
