package com.example.demo.model;

import lombok.Data;

public record UserDto(Long id, String username, String email) {
}