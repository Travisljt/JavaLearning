package com.example.demo;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
public class StudentController {

    @PostMapping("/students")
    public String create(@RequestBody @Valid APIStudent student) {

        return "执行create操作";
    }

    @GetMapping("/students/{studentId}")
    public String read(@PathVariable @Min(100) Integer studentId) {
        return "执行read操作";
    }

    @PutMapping("/students/{studentId}")
    public String update(@PathVariable Integer studentId,
                         @RequestBody APIStudent student){

        return "执行update操作";
    }

    @DeleteMapping("/students/{studentId}")
    public String delete(@PathVariable Integer studentId){
        return "执行delete操作";
    }

}
