package com.entity.controller;


import java.net.URI;
import java.util.Optional;

import com.entity.dao.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.entity.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/department")
public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@org.springframework.web.bind.annotation.RequestBody @Valid Department department) {
        Department savedDepartment = departmentService.create(department);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedDepartment.getId()).toUri();
        return ResponseEntity.created(location).body(savedDepartment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        Optional<Department> optionalDepartment = departmentService.findById(id);
        if (!optionalDepartment.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        } else {
            return ResponseEntity.ok().body(optionalDepartment.get());
        }
    }

    @GetMapping()
    public ResponseEntity<Page<Department>> getAll(Pageable pageable) {
        Page<Department> departmentPage = departmentService.findAll(pageable);
        return ResponseEntity.ok().body(departmentPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody Department department) {
        Optional<Department> optionalDepartment = departmentService.findById(id);
        if (!optionalDepartment.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        department.setId(optionalDepartment.get().getId());
        departmentService.create(department);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        Optional<Department> departmentOptional = departmentService.findById(id);
        if (!departmentOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        departmentService.delete(departmentOptional.get());
        return ResponseEntity.noContent().build();
    }

}
