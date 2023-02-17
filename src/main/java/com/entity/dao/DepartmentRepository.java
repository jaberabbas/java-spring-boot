package com.entity.dao;

import com.entity.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DepartmentRepository extends JpaRepository<Department, Long> {


    public List<Department> findDepartmentByName(String name);
}
