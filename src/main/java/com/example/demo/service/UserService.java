// src/main/java/com/example/demo/service/UserService.java
package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.User;
import com.example.demo.dto.UserDto;
import com.example.demo.mapper.UserMapper;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final RestTemplate restTemplate;
    public UserService(UserMapper userMapper, RestTemplate restTemplate) {
        this.userMapper = userMapper;
        this.restTemplate = restTemplate;   
    }

    public UserDto convertToDto(User user) {
        return userMapper.userToUserDto(user);
    }

    public User convertToEntity(UserDto userDto) {
        return userMapper.userDtoToUser(userDto);
    }
    public String getUserData() {
        String url = "http://localhost:8080/api/users/toDto";
        return restTemplate.getForObject(url, String.class);
    }
}
