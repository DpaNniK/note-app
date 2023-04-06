package com.example.user.service;

import com.example.user.dto.UserDto;
import com.example.user.dto.UserResultDto;
import com.example.user.dto.UserAdminUpdateDto;
import com.example.user.dto.UserUpdateDto;
import com.example.user.models.User;

public interface UserService {

    UserResultDto createNewUser(UserDto userDto);

    UserResultDto changeUser(Integer userId, UserAdminUpdateDto userUpdateDto);

    UserResultDto changeProfile(String email, UserUpdateDto userUpdateDto);

    User getUserByEmail(String email);

    void deleteUser(Integer userId);

    User getUserById(Integer userId);
}
