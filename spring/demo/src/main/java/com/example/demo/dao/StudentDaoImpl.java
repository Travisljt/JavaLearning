package com.example.demo.dao;

import com.example.demo.Student;
import com.example.demo.StudetnRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudentDaoImpl implements StudentDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Student getById(Integer studentId) {
        String sql = "SELECT id, name FROM student WHERE id = :studentId";
        Map<String,Object> map = new HashMap<>();
        map.put("studentId",studentId);
        List<Student> list = namedParameterJdbcTemplate.query(sql,map,new StudetnRowMapper());
        if(list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }

}
