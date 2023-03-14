package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<JPAStudent,Integer> {
}
