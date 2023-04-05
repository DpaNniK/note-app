package com.example.user.controllers;

import com.example.user.dto.UserDto;
import com.example.user.dto.UserResultDto;
import com.example.user.dto.UserAdminUpdateDto;
import com.example.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/admins")
@PreAuthorize("hasAuthority('access:admin')")
public class AdminController {

    private UserService userService;

    //Возвращаю сущность UserResultDto для наглядности работы при использовании swagger
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResultDto createNewUser(@RequestBody @Valid UserDto userDto) {
        return userService.createNewUser(userDto);
    }

    //Изменение пользователя, здесь же можно сделать пользователя администратором,
    //передав в UserUpdateDto роль
    @PatchMapping("/{userId}")
    public UserResultDto changeUser(@PathVariable Integer userId,
                                    @RequestBody UserAdminUpdateDto userUpdateDto) {
        return userService.changeUser(userId, userUpdateDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }
}
