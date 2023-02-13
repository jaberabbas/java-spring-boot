package com.task.controller;

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

    @Autowired
    private EmployeeService employeeService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid Employee employee) {
        ResponseEntity responseEntity =  employeeService.create(employee);
        return  responseEntity;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        ResponseEntity<?> responseEntity = employeeService.getById(id);
        return responseEntity;
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        //TODO use pagination with web client
        ResponseEntity<?> responseEntity = employeeService.getAll();
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid Employee employee) {
        ResponseEntity<?> responseEntity = employeeService.update(id, employee);
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        ResponseEntity responseEntity = employeeService.delete(id);
        return responseEntity;
    }
}
