package com.task.service;

import com.task.model.Employee;
import com.task.util.WebClientConnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmployeeService {

    @Autowired
    private WebClientConnector webClientConnector;

    public ResponseEntity<?> getAll() {
        return webClientConnector.getAll("http://localhost:8080/employee", Employee.class).block();
    }

    public ResponseEntity<?> getById(long id) {
        ResponseEntity<?> responseEntity = webClientConnector.getById("http://localhost:8080/employee/" + id, Object.class)
                .block();
        log.debug("Contents of responseEntity are {}", responseEntity);
        return responseEntity;
    }

    public ResponseEntity<?> create(Employee employee) {
        HttpEntity<Employee> request = new HttpEntity<>(employee);
        return webClientConnector.create("http://localhost:8080/employees", request, Object.class).block();
    }

    public ResponseEntity<?> delete(long id) {
        return webClientConnector.delete("http://localhost:8080/employees/" + id, Object.class).block();
    }

    public ResponseEntity<?> update(Long id, Long dptId, Employee employeeDto) {
        HttpEntity<Employee> request = new HttpEntity<>(employeeDto);
        return webClientConnector
                .update("http://localhost:8080/employees/" + id + "/" + dptId, request, Employee.class)
                .block();
    }

}
