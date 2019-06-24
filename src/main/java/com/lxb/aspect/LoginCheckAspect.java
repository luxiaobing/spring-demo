package com.lxb.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2019-06-01 12:52
 **/
@Slf4j
@Component
@Aspect
public class LoginCheckAspect {

    @Pointcut("execution(* com.lxb.service.*.*(..))")
    private void loginCheck() {

    }

    @Before(value = "loginCheck()")
    public void before(JoinPoint joinPoint) throws Throwable {
        log.info("begin before 方法");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //如果方法体上使用了DataSource注解
        if (method.isAnnotationPresent(LoginCheck.class)) {

            LoginCheck loginCheck = method.getAnnotation(LoginCheck.class);
            //获取该方法上的注解名
            if(loginCheck.value().equals("111")){
                log.info("校验通过");
            }else{
                log.info("校验未通过");

            }

        }
    }

    @Around(value = "loginCheck()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("method start time:" + System.currentTimeMillis());
        Object proceed = joinPoint.proceed();

        log.info("method end time:" + System.currentTimeMillis());
        return proceed;

    }

}
