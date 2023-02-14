package com.task.service;

import com.task.model.Department;
import com.task.model.Employee;
import com.task.util.WebClientConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private WebClientConnector webClientConnector;

    private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);

    public ResponseEntity<?> create(Department department) {
        HttpEntity<Department> request = new HttpEntity<>(department);
        return webClientConnector.create("/department", request);
    }


    public ResponseEntity<?> getById(long id) {
        return webClientConnector.getById("/department/" + id);
    }

    public ResponseEntity<?> getAll() {
        return webClientConnector.getAll("/department");
    }


    public ResponseEntity<?> update(Long id, Department department) {
        HttpEntity<Department> request = new HttpEntity<>(department);
        return webClientConnector.update("/department/" + id, request);
    }

    public ResponseEntity<?> delete(long id) {
        return webClientConnector.delete("/department/" + id);
    }
}
