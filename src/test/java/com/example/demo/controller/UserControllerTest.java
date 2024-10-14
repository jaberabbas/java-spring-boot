package com.example.demo.controller;

import com.example.demo.model.UserDto;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void convertToDto() throws Exception {
        UserDto userDto = new UserDto(1L, "Jon", "jon@gmail.com");
        given(userService.convertToDto(any(User.class))).willReturn(userDto);
        mockMvc.perform(post("/api/users/to-dto").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(header().string("location", "http://localhost/api/users/to-dto/1"));

        verify(userService).convertToDto(any(User.class));
    }

    @Test
    void convertToUser() throws Exception {
        User user = new User(1L, "Jon", "jon@gmail.com", "ddd");
        given(userService.convertToEntity(any(UserDto.class))).willReturn(user);
        mockMvc.perform(post("/api/users/to-entity").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(header().string("location", "http://localhost/api/users/to-entity/1"));

        verify(userService).convertToEntity(any(UserDto.class));
    }

    protected static String asJsonString(final Object obj) throws JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    @Test
    void convertToEntity() {
    }
}