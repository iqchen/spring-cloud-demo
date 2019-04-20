package com.spring.cloud.eureka.client.proxy;

import org.junit.Test;

public class DynamicProxyTest /*extends BaseTest*/ {

    @Test
    public void testJDKProxy() {
        // 实例化目标对象
        UserService userService = new UserServiceImpl();
        JDKProxy invocationHandler = new JDKProxy(userService);
        // 根据目标对象生成代理对象
        UserService proxy = (UserService) invocationHandler.getProxy();
        // 调用代理对象的方法
        System.out.println(proxy.add());
    }

    @Test
    public void testCglibProxy() {
        // 实例化目标对象
        User userService = new User();
        // 根据目标对象生成代理对象
        User user = (User) new CglibProxy(userService).getProxy();
        // 调用代理对象的方法
        System.out.println(user.add("test"));
    }

}
interface UserService {

    /**
     * 目标方法
     */
    String add();

}
/**
 * 目标对象
 */
class UserServiceImpl implements UserService {

    public String add() {
        System.out.println("call UserServiceImpl add method");
        return "test";
    }
}

/**
 * 目标对象
 */
class User {

    public String add(String name) {
        System.out.println("call User add method");
        return name;
    }
}