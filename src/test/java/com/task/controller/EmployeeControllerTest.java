package com.task.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.model.Department;
import com.task.model.Employee;
import com.task.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void testCreate() throws JsonProcessingException, Exception {
        Department department = new Department(Long.valueOf(1), "dept", "Liege", "department controller test unit");
        Employee employee = new Employee(Long.valueOf(1), "James", "Bond", Double.valueOf("10000"), 55, "SalesMan", department);
        URI location = URI.create("http://localhost/employee");
        ResponseEntity responseEntity = ResponseEntity.created(location).body(employee);
        given(employeeService.create(ArgumentMatchers.any(Employee.class))).willReturn(responseEntity);
        mockMvc.perform(post("/employee").contentType(MediaType.APPLICATION_JSON).content(asJsonString(employee)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/employee"));
    }


    @Test
    public void testGet() throws Exception {
        Department department = new Department(Long.valueOf(1), "dept", "department controller test unit", "Liege");
        Employee employee = new Employee(Long.valueOf(1), "James", "Bond", Double.valueOf("10000"), 55, "SalesMan", department);
        ResponseEntity responseEntity = ResponseEntity.ok().body(employee);
        given(employeeService.getById(anyLong())).willReturn(responseEntity);
        mockMvc.perform(get("/employee/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("firstName").value("James"))
                .andExpect(jsonPath("lastName").value("Bond"))
                .andExpect(jsonPath("age").value("55"))
                .andExpect(jsonPath("jobTitle").value("SalesMan"))
                .andExpect(jsonPath("salary").value("10000.0"))
                .andExpect(jsonPath("$.department.name").value("dept"))
                .andExpect(jsonPath("$.department.location").value("Liege"))
                .andExpect(jsonPath("$.department.description").value("department controller test unit"));

    }

    @Test
    public void testGetAll() throws Exception {
        Department department1 = new Department(Long.valueOf(1), "dept1", "department controller test unit", "Liege");
        Department department2 = new Department(Long.valueOf(2), "dept2", "department controller test unit", "Namur");
        Employee employee1 = new Employee(Long.valueOf(1), "James", "Bond", Double.valueOf("10000"), 55, "SalesMan", department1);
        Employee employee2 = new Employee(Long.valueOf(2), "Mister", "Beens", Double.valueOf("5000"), 60, "Comedian", department2);

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);
        ResponseEntity responseEntity = ResponseEntity.ok().body(employeeList);
        given(employeeService.getAll()).willReturn(responseEntity);
        mockMvc.perform(get("/employee"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].firstName").value("James"))
                .andExpect(jsonPath("$.[0].lastName").value("Bond"))
                .andExpect(jsonPath("$.[0].department.name").value("dept1"))
                .andExpect(jsonPath("$.[0].department.location").value("Liege"))

                .andExpect(jsonPath("$.[1].firstName").value("Mister"))
                .andExpect(jsonPath("$.[1].lastName").value("Beens"))
                .andExpect(jsonPath("$.[1].department.name").value("dept2"))
                .andExpect(jsonPath("$.[1].department.location").value("Namur"));
    }

    @Test
    public void testUpdate() throws Exception {
        Department department = new Department(Long.valueOf(1), "dept", "department controller test unit", "Liege");
        Employee employee = new Employee(Long.valueOf(1), "James", "Bond", Double.valueOf("10000"), 55, "SalesMan", department);
        ResponseEntity responseEntity = ResponseEntity.noContent().build();
        given(employeeService.update(anyLong(), ArgumentMatchers.any(Employee.class))).willReturn(responseEntity);
        mockMvc.perform(put("/employee/{id}", "1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(employee)))
                .andExpect(status().isNoContent());

    }

    @Test
    public void testDelete()throws Exception{
        given(employeeService.delete(anyLong())).willReturn(ResponseEntity.noContent().build());
        mockMvc.perform(delete("/employee/{id}", "1"))
                .andExpect(status().isNoContent());
    }
    private String asJsonString(final Object obj) throws JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(obj);
        return jsonContent;

    }
}
