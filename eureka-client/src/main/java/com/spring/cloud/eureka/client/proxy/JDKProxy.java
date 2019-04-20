package com.spring.cloud.eureka.client.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy implements InvocationHandler {

    private final static Logger logger = LoggerFactory.getLogger(JDKProxy.class);

    // 目标对象
    private Object target;

    public JDKProxy(Object target) {
        super();
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在目标对象的方法执行之前操作，记录日志等等
        logger.info("call proxy class={}, Method={}, Args={}", proxy, method, args);
        // 执行目标对象的方法
        Object result = method.invoke(target, args);

        logger.info("Object invoke success, return={}", result);
        return result;
    }

    /**
     * 获取目标对象的代理对象
     * @return 代理对象
     */
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(), this);
    }
}
