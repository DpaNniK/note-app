package com.example.user.controllers;

import com.example.user.dto.UserResultDto;
import com.example.user.dto.UserUpdateDto;
import com.example.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@PreAuthorize("hasAuthority('access:users')")
public class UserController {

    private final UserService userService;

    //Та самая возможность пользователя управлять своим профилем
    @PatchMapping("/change")
    public UserResultDto changeProfile(@RequestBody UserUpdateDto userUpdateDto,
                                       Principal principal) {
        return userService.changeProfile(principal.getName(), userUpdateDto);
    }
}
