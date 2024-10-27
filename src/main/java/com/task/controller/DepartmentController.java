package com.task.controller;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.task.model.Department;
import com.task.service.DepartmentService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/department")
public class DepartmentController {


    private final DepartmentService departmentService;
    private final static String DEPARTMENT_SERVICE = "departmentService";
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @Retry(name = DEPARTMENT_SERVICE)
    @CircuitBreaker(name = DEPARTMENT_SERVICE)
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid Department department) {
        return departmentService.create(department);
    }

    @Retry(name = DEPARTMENT_SERVICE)
    @CircuitBreaker(name = DEPARTMENT_SERVICE)
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        return departmentService.getById(id);
    }

    @Retry(name = DEPARTMENT_SERVICE)
    @CircuitBreaker(name = DEPARTMENT_SERVICE)
    @GetMapping()
    public ResponseEntity<?> getAll() {
        return departmentService.getAll();
    }

    @Retry(name = DEPARTMENT_SERVICE)
    @CircuitBreaker(name = DEPARTMENT_SERVICE)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid Department department) {
        return departmentService.update(id, department);
    }

    @Retry(name = DEPARTMENT_SERVICE)
    @CircuitBreaker(name = DEPARTMENT_SERVICE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return departmentService.delete(id);
    }

}
