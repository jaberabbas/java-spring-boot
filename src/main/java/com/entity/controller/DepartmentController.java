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


@Slf4j
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
        log.debug("create start: " + department);
        Department savedDepartment = departmentService.create(department);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedDepartment.getId()).toUri();
        log.debug("create end: " + location + " " + savedDepartment);
        return ResponseEntity.created(location).body(savedDepartment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        log.debug("getById start: " + id);
        Optional<Department> optionalDepartment = departmentService.findById(id);
        if (!optionalDepartment.isPresent()) {
            log.debug("getById end unprocessableEntity");
            return ResponseEntity.unprocessableEntity().build();
        } else {
            log.debug("getById end: " + optionalDepartment.get());
            return ResponseEntity.ok().body(optionalDepartment.get());
        }
    }

    @GetMapping()
    public ResponseEntity<Page<Department>> getAll(Pageable pageable) {
        log.debug("getAll start");
        Page<Department> departmentPage = departmentService.findAll(pageable);
        log.debug("getAll end: " + departmentPage.getTotalElements());
        return ResponseEntity.ok().body(departmentPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody Department department) {
        log.debug("update start id: " + id + " department: " + department);
        Optional<Department> optionalDepartment = departmentService.findById(id);
        if (!optionalDepartment.isPresent()) {
            log.debug("update end unprocessableEntity");
            return ResponseEntity.unprocessableEntity().build();
        }
        department.setId(optionalDepartment.get().getId());
        departmentService.create(department);
        log.debug("update end: " + department);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        log.debug("delete start: " + id);
        Optional<Department> departmentOptional = departmentService.findById(id);
        if (!departmentOptional.isPresent()) {
            log.debug("delete end unprocessableEntity");
            return ResponseEntity.unprocessableEntity().build();
        }
        departmentService.delete(departmentOptional.get());
        log.debug("delete end: ok");
        return ResponseEntity.noContent().build();
    }

}
