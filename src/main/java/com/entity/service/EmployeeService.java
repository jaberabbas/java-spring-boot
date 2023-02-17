package com.entity.service;

import java.util.Optional;
import com.entity.dao.DepartmentRepository;
import com.entity.model.Employee;
import com.entity.dao.EmployeeRepository;
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

    public Employee create(Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }
    public Optional<Employee> findById(long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee;
    }
    public Page<Employee> findAll(Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return employeePage;
    }
    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }
}
