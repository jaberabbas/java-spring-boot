package com.task.controller;


import org.springframework.beans.factory.annotation.Autowired;
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
    public DepartmentController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
       return departmentService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable ("id") long id) {
        return departmentService.getById(id);
    }
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid Department department) {
        ResponseEntity<?> response = departmentService.create(department);
        return response;
    }
}
