// src/main/java/com/example/demo/controller/UserController.java
package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.User;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/toDto")
    public UserDto convertToDto(@RequestBody User user) {
        return userService.convertToDto(user);
    }

    @PostMapping("/toEntity")
    public User convertToEntity(@RequestBody UserDto userDto) {
        return userService.convertToEntity(userDto);
    }
}

