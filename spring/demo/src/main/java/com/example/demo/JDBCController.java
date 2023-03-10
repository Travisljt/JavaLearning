package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class JDBCController {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @PostMapping("/newStudent")
    public String insert(@RequestBody Student student){

        String sql = "INSERT INTO student(name) VALUE (:studentName)";
        Map<String,Object> map = new HashMap<>();
        map.put("studentName",student.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

        int id = keyHolder.getKey().intValue();
        System.out.println("mysql generate id: " + id);

        return "执行 insert";
    }


    @PostMapping("/newStudent/batch")
    public String insertList(@RequestBody List<Student> studentList){

        String sql = "INSERT INTO student (name) VALUE (:studentName)";
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[studentList.size()];

        for(int i=0;i<studentList.size();i++){
            Student student = studentList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("studentName",student.getName());
        }

        namedParameterJdbcTemplate.batchUpdate(sql,parameterSources);
        return "执行一批 insert sql";
    }


    @DeleteMapping("/newStudent/{studentId}")
    public String delete(@PathVariable Integer studentId){
        String sql = "DELETE FROM student WHERE id = :studentId";
        Map<String,Object> map = new HashMap<>();
        map.put("studentId",studentId);
        namedParameterJdbcTemplate.update(sql,map);

        return "执行 delete";
    }
}
