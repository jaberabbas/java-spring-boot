package com.task.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.model.Department;
import com.task.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Test
    public void testCreate() throws JsonProcessingException, Exception {
        Department department = new Department(Long.valueOf(1), "dept", "Liege", "department controller test unit");
        URI location = URI.create("http://localhost/department");
        ResponseEntity responseEntity = ResponseEntity.created(location).body(department);
        given(departmentService.create(ArgumentMatchers.any(Department.class))).willReturn(responseEntity);
        mockMvc.perform(post("/department").contentType(MediaType.APPLICATION_JSON).content(asJsonString(department)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/department"));
    }


    @Test
    public void testGet() throws Exception {
        Department department = new Department(Long.valueOf(1), "dept", "department controller test unit", "Liege");
        ResponseEntity responseEntity = ResponseEntity.ok().body(department);
        given(departmentService.getById(anyLong())).willReturn(responseEntity);
        mockMvc.perform(get("/department/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name").value("dept"))
                .andExpect(jsonPath("location").value("Liege"))
                .andExpect(jsonPath("description").value("department controller test unit"));

    }

    @Test
    public void testGetAll() throws Exception {
        Department department1 = new Department(Long.valueOf(1), "dept1", "department controller test unit", "Liege");
        Department department2 = new Department(Long.valueOf(2), "dept2", "department controller test unit", "Namur");
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(department1);
        departmentList.add(department2);
        ResponseEntity responseEntity = ResponseEntity.ok().body(departmentList);
        given(departmentService.getAll()).willReturn(responseEntity);
        mockMvc.perform(get("/department"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("dept1"))
                .andExpect(jsonPath("$.[0].location").value("Liege"))
                .andExpect(jsonPath("$.[1].name").value("dept2"))
                .andExpect(jsonPath("$.[1].location").value("Namur"));
    }

    @Test
    public void testUpdate() throws Exception {
        Department department = new Department(Long.valueOf(1), "dept", "department controller test unit", "Liege");
        ResponseEntity responseEntity = ResponseEntity.noContent().build();
        given(departmentService.update(anyLong(), ArgumentMatchers.any(Department.class))).willReturn(responseEntity);
        mockMvc.perform(put("/department/{id}", "1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(department)))
                .andExpect(status().isNoContent());

    }

    @Test
    public void testDelete()throws Exception{
        given(departmentService.delete(anyLong())).willReturn(ResponseEntity.noContent().build());
        mockMvc.perform(delete("/department/{id}", "1"))
                .andExpect(status().isNoContent());
    }
    private String asJsonString(final Object obj) throws JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(obj);
        return jsonContent;

    }
}
