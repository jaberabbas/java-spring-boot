package com.task.controller;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:9090")
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid Employee employee) {
        log.debug("create start: " + employee);
        ResponseEntity responseEntity =  employeeService.create(employee);
        log.debug("create end: " + responseEntity.getBody());
        return  responseEntity;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        log.debug("get start: " + id);
        ResponseEntity<?> responseEntity = employeeService.getById(id);
        log.debug("get end: " + responseEntity.getBody());
        return responseEntity;
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        log.debug("getAll start");
        //TODO use pagination with web client
        ResponseEntity<?> responseEntity = employeeService.getAll();
        log.debug("getAll end: " + responseEntity.getBody());
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid Employee employee) {
        log.debug("update start: " + id + " " + employee);
        ResponseEntity<?> responseEntity = employeeService.update(id, id, employee);
        log.debug("update end: " + responseEntity.getBody());
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        log.debug("delete start: " + id);
        ResponseEntity responseEntity = employeeService.delete(id);
        log.debug("delete end: " + responseEntity.getBody());
        return responseEntity;
    }
}
