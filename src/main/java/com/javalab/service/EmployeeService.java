package com.javalab.service;

import java.util.Optional;
import com.javalab.dao.DepartmentRepository;
import com.javalab.dao.Employee;
import com.javalab.dao.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public Page<Employee> getAll(Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return employeePage;
    }

    public Optional<Employee> findById(long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee;
    }

    public Employee create(Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }

    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }
}