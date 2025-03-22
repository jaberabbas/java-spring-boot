package com.task.controller;


import com.task.util.AsyncLogger;
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
    private final AsyncLogger asyncLogger;

    public DepartmentController(DepartmentService departmentService, AsyncLogger asyncLogger) {
        this.departmentService = departmentService;
        this.asyncLogger = asyncLogger;
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
        Department dep = new Department(1L, "dep1", "it department", "Belgium");
        //asyncLogger.logInfo("get method called with id: " + id, dep);
        Exception e =  new Exception("this is a testing exception from Department Controller");
        asyncLogger.logError("this is error msg: {}, cause: {}", e.getMessage(), e.getCause());


        asyncLogger.logDebug("this is a debug id: {} and  department: {} )", dep.getId(), dep);
        asyncLogger.logInfo("this is a info id: {}, name: {} and department: {} )", dep.getId(), dep.getName(), dep);
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
