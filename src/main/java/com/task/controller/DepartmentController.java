package com.task.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.task.model.Department;
import com.task.service.DepartmentService;
import jakarta.validation.Valid;

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
    public ResponseEntity<?> create(@RequestBody @Valid Department department) {
        log.debug("create start: " + department);
        ResponseEntity<?> responseEntity = departmentService.create(department);
        log.debug("create end: " + responseEntity.getBody());
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        log.debug("get start: " + id);
        ResponseEntity<?> responseEntity = departmentService.getById(id);
        log.debug("get end:" + responseEntity.getBody());
        return  responseEntity;
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        log.debug("getAll start");
        ResponseEntity<?> responseEntity = departmentService.getAll();
        log.debug("getAll end: " + responseEntity.getBody() );
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid Department department) {
        log.debug("update start: " + id + " " + department);
        ResponseEntity<?> responseEntity = departmentService.update(id, id, department);
        log.debug("update end: " + responseEntity.getBody());
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        log.debug("delete start: " + id);
        ResponseEntity responseEntity = departmentService.delete(id);
        log.debug("delete end: " + responseEntity.getBody());
        return responseEntity;
    }

}
