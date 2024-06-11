package com.example.project.authentication.user;

import com.example.project.authentication.user.User;
import com.example.project.authentication.user.UserDto;

public interface UserMapper {

    UserDto toUserDto(User user);
}