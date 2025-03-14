package com.task.service;

import com.task.model.Employee;
import com.task.util.WebClientConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class EmployeeService {

    @Autowired
    private WebClientConnector webClientConnector;

    public ResponseEntity<?> create(Employee employee) {
        HttpEntity<Employee> request = new HttpEntity<>(employee);
        return webClientConnector.create("/employee", request);
    }

    public ResponseEntity<?> getById(long id) {
        return webClientConnector.getById("/employee/" + id);
    }

    public ResponseEntity<?> getAll() {
        return webClientConnector.getAll("/employee");
    }


    public ResponseEntity<?> update(Long id, Employee employee) {
        HttpEntity<Employee> request = new HttpEntity<>(employee);
        return webClientConnector.update("/employee/" + id, request);
    }

    public ResponseEntity<?> delete(long id) {
        return webClientConnector.delete("/employee/" + id);
    }
    public String perfTestMethod1(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "ok";
    }
    public String perfTestMethod2(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "ok";
    }
    public String perfTestMethod3(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "ok";
    }
}
