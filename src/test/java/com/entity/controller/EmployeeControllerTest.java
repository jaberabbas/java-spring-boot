package com.entity.controller;

import com.entity.model.Department;
import com.entity.model.Employee;
import com.entity.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@AutoConfigureDataJpa
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void testCreate() throws JsonProcessingException, Exception {
        Department department = new Department(Long.valueOf(1), "dept", "dept mock MVC test", "London", null);
        Employee employee = new Employee(Long.getLong("1"), "Jon", "Smith", Double.valueOf("3000"), 40, "developer", department);
        given(employeeService.create(any(Employee.class))).willReturn(employee);
        mockMvc.perform(post("/employee").contentType(MediaType.APPLICATION_JSON).content(asJsonString(employee)))
                .andExpect(status().isCreated()).andExpect(header().string("location", "http://localhost/employee/"));

        verify(employeeService).create(any(Employee.class));
    }

    @Test
    public void testGet() throws Exception {
        Department department = new Department(Long.valueOf(1), "dept", "dept mock MVC test", "London", null);
        Employee employee = new Employee(Long.valueOf(1), "Jon", "Smith", Double.valueOf("3000"), 40, "Developer", department);
        given(employeeService.findById(anyLong())).willReturn(Optional.of(employee));
        mockMvc.perform(get("/employee/{id}", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("firstName").value("Jon"))
                .andExpect(jsonPath("lastName").value("Smith"))
                .andExpect(jsonPath("age").value("40"))
                .andExpect(jsonPath("jobTitle").value("Developer"))
                .andExpect(jsonPath("$.department.name").value("dept"))
                .andExpect(jsonPath("$.department.location").value("London"));


        verify(employeeService).findById(anyLong());
    }

    @Test
    public void testGetNotFound() throws Exception {
        Department department = new Department(Long.valueOf(1), "dept", "dept mock MVC test", "London", null);
        given(employeeService.findById(anyLong())).willReturn(Optional.empty());
        mockMvc.perform(get("/employee/{id}", "2"))
                .andExpect(status().isUnprocessableEntity());

        verify(employeeService).findById(anyLong());
    }

    @Test
    public void testGetAll() throws Exception {
        Department department1 = new Department(Long.valueOf(1), "dept1", "dept mock MVC test", "London", null);
        Department department2 = new Department(Long.valueOf(2), "dept2", "dept mock MVC test", "Paris", null);
        Employee employee1 = new Employee(Long.getLong("1"), "Jon", "Smith", Double.valueOf("3000"), 40, "Developer", department1);
        Employee employee2 = new Employee(Long.getLong("2"), "James", "Bond", Double.valueOf("4000"), 33, "Manager", department2);

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);
        Page<Employee> employeePage = new PageImpl<>(employeeList);
        given(employeeService.findAll(any(Pageable.class))).willReturn(employeePage);
        mockMvc.perform(get("/employee")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content.[0].firstName").value("Jon"))
                .andExpect(jsonPath("$.content.[0].lastName").value("Smith"))
                .andExpect(jsonPath("$.content.[1].firstName").value("James"))
                .andExpect(jsonPath("$.content.[1].lastName").value("Bond"));

        verify(employeeService).findAll(any(Pageable.class));
    }

    @Test
    public void testUpdate() throws Exception {
        Department department = new Department(Long.valueOf(1), "dept", "dept mock MVC test", "London", null);
        Employee employee = new Employee(Long.valueOf(1), "Jon", "Smith", Double.valueOf("3000"), 40, "Developer", department);
        given(employeeService.findById(anyLong())).willReturn(Optional.of(employee));
        given(employeeService.create(any(Employee.class))).willReturn(employee);
        mockMvc.perform(put("/employee/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(department)))
                .andExpect(status().isNoContent());

        verify(employeeService).findById(anyLong());
        verify(employeeService).create(any(Employee.class));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        Department department = new Department(Long.valueOf(1), "dept", "dept mock MVC test", "London", null);
        Employee employee = new Employee(Long.getLong("1"), "Jon", "Smith", Double.valueOf("3000"), 40, "Developer", department);
        given(employeeService.findById(anyLong())).willReturn(Optional.empty());
        mockMvc.perform(put("/employee/{id}", "1").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee))).andExpect(status().isUnprocessableEntity());

        verify(employeeService).findById(anyLong());
    }

    @Test
    public void testDelete() throws Exception {
        Department department = new Department(Long.valueOf(1), "dept", "dept mock MVC test", "London", null);
        Employee employee = new Employee(Long.getLong("1"), "Jon", "Smith", Double.valueOf("3000"), 40, "Developer", department);
        given(employeeService.findById(anyLong())).willReturn(Optional.of(employee));
        mockMvc.perform(delete("/employee/{id}", "1"))
                .andExpect(status().isNoContent());

        verify(employeeService).findById(anyLong());

    }

    @Test
    public void testDeleteNotFound() throws Exception {
        given(employeeService.findById(anyLong())).willReturn(Optional.empty());
        mockMvc.perform(delete("/employee/{id}", "1"))
                .andExpect(status().isUnprocessableEntity());

        verify(employeeService).findById(anyLong());

    }

    protected static String asJsonString(final Object obj) throws JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(obj);
        return jsonContent;

    }

}
