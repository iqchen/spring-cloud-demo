package com.spring.cloud.eureka.client.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LogAspect {

    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution (* com.spring.cloud.eureka.client.service.impl..*.*(..))")
    private void  writeLog() { }

    @Before("writeLog()")
    public void beforeLog() {
        logger.info("call before log");
    }
    @AfterReturning("writeLog()")
    public void afterReturnLog() {
        logger.info("call after return log");
    }
    @After("writeLog()")
    public void afterLog() {
        logger.info("call after log");
    }
    @Around("writeLog()")
    public Object aroundLog(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("starting call around log");
        Object proceed = pjp.proceed();
        logger.info("finished call around log");
        return proceed;
    }
    @AfterThrowing("writeLog()")
    public void afterThrowingLog() {
        logger.info("call after throwing log");
    }
}
