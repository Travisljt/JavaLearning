package com.example.demo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class MyAspect {

//    @Before("execution(* com.example.demo.HpPrinter.*(..))")
//    public void before(){
//        System.out.println("I am before");
//    }
//
//    @After("execution(* com.example.demo.HpPrinter.*(..))")
//    public void after(){
//        System.out.println("I am after");
//    }

    @Around("execution(* com.example.demo.HpPrinter.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        Date start = new Date();

        //执行方法体，obj就是hpPrinter的对象
        Object obj = pjp.proceed();

        Date end = new Date();
        long time = end.getTime()- start.getTime();
        System.out.println("执行时间为："+time + "ms");
        return obj;
    }
}
