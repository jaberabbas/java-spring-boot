package com.task.service;

import com.task.model.Department;
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

    public ResponseEntity<?> getAll() {
        ResponseEntity<?> department = webClientConnector.getAll("http://localhost:8080/department", Department.class)
                .block();
        return department;
    }
    public ResponseEntity<?> getById(long id) {
        ResponseEntity<?> responseEntity = webClientConnector.getById("http://localhost:8080/department/" + id, Object.class)
                .block();
        log.debug("Contents of responseEntity are {}", responseEntity);
        return responseEntity;
    }

    public ResponseEntity<?> create(Department department) {
            HttpEntity<Department> request = new HttpEntity<>(department);
            ResponseEntity<?> response = webClientConnector.create("http://localhost:8080/department", request, Department.class)
                    .block();
            return response;
    }

}
