package com.task.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.task.model.Department;
import com.task.service.DepartmentService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/department")
public class DepartmentController {


    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid Department department) {
        ResponseEntity<?> responseEntity = departmentService.create(department);
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        ResponseEntity<?> responseEntity = departmentService.getById(id);
        return  responseEntity;
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        ResponseEntity<?> responseEntity = departmentService.getAll();
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid Department department) {
        ResponseEntity<?> responseEntity = departmentService.update(id, department);
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        ResponseEntity responseEntity = departmentService.delete(id);
        return responseEntity;
    }

}
