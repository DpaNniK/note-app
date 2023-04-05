package com.example.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность нового пользователя")
public class UserDto {
    @Schema(description = "Имя пользователя", example = "Иван")
    @NotBlank
    private String name;
    @Schema(description = "Фамилия пользователя", example = "Петров")
    @NotBlank
    private String surname;
    @NotBlank
    @Schema(description = "Пароль пользователя", example = "123456")
    private String password;
    @Email
    @Schema(description = "Email пользователя", example = "ivanpet@yandex.ru")
    private String email;
}
