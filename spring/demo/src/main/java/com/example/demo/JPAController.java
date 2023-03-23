package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import javax.persistence.criteria.CriteriaBuilder;

@RestController
public class JPAController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/jpaStudent")
    public String insert(@RequestBody JPAStudent student){
        studentRepository.save(student);
        return "执行jpa create操作";
    }

    @PutMapping("/jpaStudent/{studentId}")
    public String update(@PathVariable Integer studentId,@RequestBody JPAStudent student){
        if(studentRepository.findById(studentId).orElse(null) == null){
            return "执行jpa update的时候出错：没有这个id的值";
        }
        student.setId(studentId);
        studentRepository.save(student);
        return "执行jpa update的操作";
    }

    @DeleteMapping("/jpaStudent/{studentId}")
    public String delete(@PathVariable Integer studentId){
        studentRepository.deleteById(studentId);
        return "执行jpa delete操作";
    }

    @GetMapping("/jpaStudent/{studentId}")
    public JPAStudent read(@PathVariable Integer studentId){
        return studentRepository.findById(studentId).orElse(null);
    }
}
