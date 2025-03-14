package com.task.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.task.model.Employee;
import com.task.service.EmployeeService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:9090")
@RequestMapping("/employee")
public class EmployeeController {

    private static final String EMPLOYEE_SERVICE = "employeeService";
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid Employee employee) {
        return employeeService.create(employee);
    }

    @Retry(name = EMPLOYEE_SERVICE)
    @CircuitBreaker(name = EMPLOYEE_SERVICE)
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        return employeeService.getById(id);
    }

    @Retry(name = EMPLOYEE_SERVICE)
    @CircuitBreaker(name = EMPLOYEE_SERVICE)
    @GetMapping()
    public ResponseEntity<?> getAll() {
        return employeeService.getAll();
    }

    @Retry(name = EMPLOYEE_SERVICE)
    @CircuitBreaker(name = EMPLOYEE_SERVICE)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid Employee employee) {
        return employeeService.update(id, employee);
    }

    @Retry(name = EMPLOYEE_SERVICE)
    @CircuitBreaker(name = EMPLOYEE_SERVICE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return employeeService.delete(id);
    }
    @GetMapping("/p")
    public String perfTestMethod(){
        employeeService.perfTestMethod1();
        employeeService.perfTestMethod2();
        employeeService.perfTestMethod3();
        return "ok";
    }
}
