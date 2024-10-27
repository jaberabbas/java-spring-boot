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

    private final EmployeeRepository employeeRepository;


    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }

    public Optional<Employee> findById(long id) {
        return employeeRepository.findById(id);
    }

    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }
}
