package com.entity.dao;

import java.util.List;

import com.entity.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    List<Employee> findByFirstNameAndLastName(String firstName, String LastName);

}
