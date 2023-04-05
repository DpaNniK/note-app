package com.example.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для обновления пользователя")
public class UserUpdateDto {
    @Schema(description = "Имя пользователя", example = "Алексей")
    private String name;
    @Schema(description = "Фамилия пользователя", example = "Сидоров")
    private String surname;
    @Schema(description = "Пароль пользователя", example = "789789")
    private String password;
    @Schema(description = "Email пользователя", example = "alex789@yandex.ru")
    private String email;
}
