package com.example.user.controllers;

import com.example.user.dto.UserResultDto;
import com.example.user.dto.UserUpdateDto;
import com.example.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@PreAuthorize("hasAuthority('access:users')")
@Tag(name = "Пользователь", description = "Эндпоинт для работы с пользователем")
@SecurityRequirement(name = "NoteAPISecureScheme")
public class UserController {

    private final UserService userService;

    //Та самая возможность пользователя управлять своим профилем
    @PatchMapping("/change")
    @Operation(
            summary = "Изменение профиля пользователя",
            description = "Позволяет пользователю изменить данные своего профиля"
    )
    public UserResultDto changeProfile(@RequestBody UserUpdateDto userUpdateDto,
                                       Principal principal) {
        return userService.changeProfile(principal.getName(), userUpdateDto);
    }
}
