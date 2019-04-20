package com.spring.cloud.eureka.client.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(JDKProxy.class);

    //目标对象
    private Object target;

    public CglibProxy(Object target) {
        this.target = target;
    }

    //给目标对象创建一个代理对象
    public Object getProxy(){
        //1.工具类
        Enhancer en = new Enhancer();
        //2.设置父类
        en.setSuperclass(target.getClass());
        //3.设置回调函数
        en.setCallback(this);
        //4.创建子类(代理对象)
        return en.create();

    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        // 在目标对象的方法执行之前操作，记录日志等等
        logger.info("call proxy class={}, Method={}, Args={}", obj, method, args);
        // 执行目标对象的方法
        Object result = method.invoke(target, args);

        logger.info("Object invoke success, return={}", result);
        return result;
    }
}
