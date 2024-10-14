// src/main/java/com/example/demo/controller/UserController.java
package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.User;
import com.example.demo.model.UserDto;
import com.example.demo.service.UserService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/to-dto")
    public ResponseEntity<?> convertToDto(@RequestBody User user) {
        UserDto userDto = userService.convertToDto(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userDto.id()).toUri();
        return ResponseEntity.created(location).body(userDto);
    }

    @PostMapping("/to-entity")
    public ResponseEntity<?> convertToEntity(@RequestBody UserDto userDto) {
        User user =  userService.convertToEntity(userDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(location).body(user);
    }
}

