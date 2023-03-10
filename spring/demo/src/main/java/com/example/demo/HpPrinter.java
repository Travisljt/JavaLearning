package com.example.demo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HpPrinter implements Printer {


//    @PostConstruct
//    public void initialize(){
//        count = 5;
//    }

    @Override
    public void print(String message) {
        System.out.println("Hp 打印机： "+ message);
    }

}
