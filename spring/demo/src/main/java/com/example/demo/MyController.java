package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyController {
//    @Autowired
//    private Printer printer;
//    @RequestMapping("/test")
//    public String test(){
//
//        //打印到控制台
//        printer.print("hello world");
//        //这个return是返回到网页里的
//        return "Hello World";
//    }

    @RequestMapping("/test1")
    public String test1(@RequestParam Integer id,
                        @RequestParam(defaultValue = "Amy") String name) {
        System.out.println("id: " + id);
        System.out.println("name: " + name);
        return "hello";
    }

    @RequestMapping("/test2")
    public String test2(@RequestBody Student student) {
        System.out.println("student id: " + student.getId());
        System.out.println("student name: " + student.getName());
        return "hi test2";
    }

    @RequestMapping("/test3")
    public String test3(@RequestHeader String info) {
        System.out.println("header info: " + info);
        return "hi test3";
    }

    @RequestMapping("/test4/{id}/{name}")
    public String test4(@PathVariable Integer id, @PathVariable String name) {
        System.out.println("Path id: " + id);
        System.out.println("Path name: " + name);
        return "hi test4";
    }

    @RequestMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("http状态码检测");
    }

    @RequestMapping("/test5")
    public String test5(){
        throw new RuntimeException("test 5 error");
    }

    @RequestMapping("/test6")
    public String test6(){
        throw new IllegalArgumentException("test 6 error");
    }

    @RequestMapping("/test7")
    public String test7(){
        System.out.println("执行test7方法");
        return "hi test7";
    }
}
