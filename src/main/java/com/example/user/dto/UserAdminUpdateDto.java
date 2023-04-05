package com.example.user.dto;

import com.example.user.models.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для обновления пользователя администратором")
public class UserAdminUpdateDto {
    @Schema(description = "Имя пользователя", example = "Илья")
    private String name;
    @Schema(description = "Фамилия пользователя", example = "Фролов")
    private String surname;
    @Schema(description = "Пароль пользователя", example = "123123")
    private String password;
    @Schema(description = "Email пользователя", example = "ilfrol@mail.ru")
    private String email;
    @Schema(description = "Новая роль пользователя(ADMIN/USER)", example = "ADMIN")
    private Role role;
}
