package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @Test
    void convertToDto() throws Exception{
        UserDto userDto = new UserDto(Long.valueOf("1"), "Jon", "jon@gmail.com");
        given(userService.convertToDto(any(User.class))).willReturn(userDto);
        mockMvc.perform(post("/api/users/to-dto")
                .accept(MediaType.parseMediaType("application/json"))
                .content(asJsonString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"));

        verify(userService).convertToDto(any(User.class));

    }
    protected static String asJsonString(final Object obj) throws JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(obj);
        return jsonContent;

    }
    @Test
    void convertToEntity() {
    }
}