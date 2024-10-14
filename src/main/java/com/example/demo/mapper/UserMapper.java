
// src/main/java/com/example/demo/mapper/UserMapper.java
package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.demo.model.User;
import com.example.demo.model.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);

    @Mapping(target = "password", constant = "")
    User userDtoToUser(UserDto userDto);
}
