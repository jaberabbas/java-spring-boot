package com.entity.controller;


import java.net.URI;
import java.util.Optional;

import com.entity.dao.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.entity.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid Employee employee) {
        log.debug("crate start: " + employee);
        Employee savedEmployee = employeeService.create(employee);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedEmployee).toUri();
        log.debug("create end, savedEmployee: " + savedEmployee + " location: " + location);
        return ResponseEntity.created(location).body(savedEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        log.debug("getById start: " + id);
        Optional<Employee> optionalEmployee = employeeService.findById(id);
        if (!optionalEmployee.isPresent()) {
            log.debug("getById end: unprocessableEntity");
            return ResponseEntity.unprocessableEntity().build();
        } else {
            log.debug("getById end: " + optionalEmployee.get());
            return ResponseEntity.ok().body(optionalEmployee.get());
        }
    }

    @GetMapping()
    public ResponseEntity<Page<Employee>> getAll(Pageable pageable) {
        log.debug("getAll start");
        Page<Employee> employeePage = employeeService.getAll(pageable);
        log.debug("getAll end:" + employeePage.getTotalElements());
        return ResponseEntity.ok().body(employeePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody Employee employee) {
        log.debug("update start, id: " + id + " employee: " + employee);
        Optional<Employee> optionalEmployee = employeeService.findById(id);
        if (!optionalEmployee.isPresent()) {
            log.debug("update end: unprocessableEntity") ;
            return ResponseEntity.unprocessableEntity().build();
        }
        employee.setId(optionalEmployee.get().getId());
        employeeService.create(employee);
        log.debug("update end: " + employee);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        log.debug("delete start: " + id);
        Optional<Employee> optionalEmployee = employeeService.findById(id);
        if (!optionalEmployee.isPresent()) {
            log.debug("delete end: unprocessableEntity");
            return ResponseEntity.unprocessableEntity().build();
        }
        employeeService.delete(optionalEmployee.get());
        log.debug("delete end: OK");
        return ResponseEntity.noContent().build();
    }

}
