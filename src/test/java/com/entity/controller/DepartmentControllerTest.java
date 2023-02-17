package com.entity.controller;

import com.entity.model.Department;
import com.entity.service.DepartmentService;
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

@WebMvcTest(DepartmentController.class)
@AutoConfigureDataJpa
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Test
    public void testCreate() throws JsonProcessingException, Exception {
        Department department = new Department(Long.valueOf(1), "dept", "dept mock MVC test", "London", null);
        given(departmentService.create(any(Department.class))).willReturn(department);
        mockMvc.perform(post("/department").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(department)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/department/1"));

        verify(departmentService).create(any(Department.class));
    }

    @Test
    public void testGet() throws Exception {
        Department department = new Department(Long.valueOf(1), "dept", "dept mock MVC test", "London", null);
        given(departmentService.findById(anyLong())).willReturn(Optional.of(department));
        mockMvc.perform(get("/department/{id}", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name").value("dept"))
                .andExpect(jsonPath("description").value("dept mock MVC test"))
                .andExpect(jsonPath("location").value("London"));
        verify(departmentService).findById(anyLong());
    }

    @Test
    public void testGetNotFound() throws Exception {
        given(departmentService.findById(anyLong())).willReturn(Optional.empty());
        mockMvc.perform(get("/department/{id}", "2"))
                .andExpect(status().isUnprocessableEntity());

        verify(departmentService).findById(anyLong());
    }

    @Test
    public void testGetAll() throws Exception {
        Department department1 = new Department(Long.valueOf(1), "dept1", "dept mock MVC test", "London", null);
        Department department2 = new Department(Long.valueOf(2), "dept2", "dept mock MVC test", "Paris", null);
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(department1);
        departmentList.add(department2);
        Page<Department> departmentPage = new PageImpl<>(departmentList);
        given(departmentService.findAll(any(Pageable.class))).willReturn(departmentPage);
        mockMvc.perform(get("/department")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content.[0].name").value("dept1"))
                .andExpect(jsonPath("$.content.[0].location").value("London"))
                .andExpect(jsonPath("$.content.[1].name").value("dept2"))
                .andExpect(jsonPath("$.content.[1].location").value("Paris"));

        verify(departmentService).findAll(any(Pageable.class));
    }

    @Test
    public void testUpdate() throws Exception {
        Department department = new Department(Long.valueOf(1), "dept", "dept mock MVC test", "London", null);
        given(departmentService.findById(anyLong())).willReturn(Optional.of(department));
        given(departmentService.create(any(Department.class))).willReturn(department);
        mockMvc.perform(put("/department/{id}", "1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(department))).andExpect(status().isNoContent());

        verify(departmentService).findById(anyLong());
        verify(departmentService).create(any(Department.class));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        Department department = new Department(Long.getLong("1"), "dept", "dept mock MVC test", "London", null);
        given(departmentService.findById(anyLong())).willReturn(Optional.empty());
        mockMvc.perform(put("/department/{id}", "1").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(department))).andExpect(status().isUnprocessableEntity());

        verify(departmentService).findById(anyLong());
    }

    @Test
    public void testDelete() throws Exception {
        Department department = new Department(Long.valueOf(1), "dept", "dept mock MVC test", "London", null);
        given(departmentService.findById(anyLong())).willReturn(Optional.of(department));
        mockMvc.perform(delete("/department/{id}", "1"))
                .andExpect(status().isNoContent());

        verify(departmentService).findById(anyLong());

    }

    @Test
    public void testDeleteNotFound() throws Exception {
        Department department = new Department(Long.valueOf(1), "dept", "dept mock MVC test", "London", null);
        given(departmentService.findById(anyLong())).willReturn(Optional.empty());
        mockMvc.perform(delete("/department/{id}", "1"))
                .andExpect(status().isUnprocessableEntity());

        verify(departmentService).findById(anyLong());

    }

    protected static String asJsonString(final Object obj) throws JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(obj);
        return jsonContent;

    }

}
