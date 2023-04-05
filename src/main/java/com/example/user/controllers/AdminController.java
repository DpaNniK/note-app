package com.example.user.controllers;

import com.example.user.dto.UserDto;
import com.example.user.dto.UserResultDto;
import com.example.user.dto.UserAdminUpdateDto;
import com.example.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/admins")
@PreAuthorize("hasAuthority('access:admin')")
@Tag(name = "Администратор", description = "Эндпоинты для работы с пользователями")
@SecurityRequirement(name = "NoteAPISecureScheme")
public class AdminController {

    private UserService userService;

    //Возвращаю сущность UserResultDto для наглядности работы при использовании swagger
    @PostMapping("/create_user")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создание пользователя",
            description = "Позволяет создать нового пользователя администратору"
    )
    public UserResultDto createNewUser(@RequestBody @Valid UserDto userDto) {
        return userService.createNewUser(userDto);
    }

    //Изменение пользователя, здесь же можно сделать пользователя администратором,
    //передав в UserUpdateDto роль
    @PatchMapping("/{userId}")
    @Operation(
            summary = "Изменение пользователя",
            description = "Позволяет администратору" +
                    " изменить данные любого пользователя," +
                    " а также присвоить ему права администратора"
    )
    public UserResultDto changeUser(@PathVariable Integer userId,
                                    @RequestBody UserAdminUpdateDto userUpdateDto) {
        return userService.changeUser(userId, userUpdateDto);
    }

    @DeleteMapping("/{userId}")
    @Operation(
            summary = "Удаление пользователя",
            description = "Позволяет администратору удалить существующего пользователя"
    )
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }
}
