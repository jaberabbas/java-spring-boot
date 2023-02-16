package com.entity.controller;

import com.entity.dao.Department;
import com.entity.service.DepartmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.headers.Header;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        Department department = new Department(Long.getLong("1"), "dept", "dept mock MVC test", "London", null);
        given(departmentService.create(any(Department.class))).willReturn(department);
        mockMvc.perform(post("/department").contentType(MediaType.APPLICATION_JSON).content(asJsonString(department)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/department/."));

        verify(departmentService).create(any(Department.class));
    }

    @Test
    public void testGet() throws Exception{
        Department department = new Department(Long.getLong("1"), "dept", "dept mock MVC test", "London", null);
        given(departmentService.findById(anyLong())).willReturn(Optional.of(department));
        mockMvc.perform(get("/department/{id}", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name").value( "dept"))
                .andExpect(jsonPath("description").value("dept mock MVC test"))
                .andExpect(jsonPath("location").value("London"))
        ;
        verify(departmentService).findById(anyLong());
    }
    protected static String asJsonString(final Object obj) throws JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(obj);
        return jsonContent;

    }

}
