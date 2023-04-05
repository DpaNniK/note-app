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
@Schema(description = "Сущность созданного пользователя")
public class UserResultDto {
    @Schema(description = "ID", example = "1")
    private Integer id;
    @Schema(description = "Имя пользователя", example = "Иван")
    private String name;
    @Schema(description = "Фамилия пользователя", example = "Петров")
    private String surname;
    @Schema(description = "Email пользователя", example = "ivanpet@yandex.ru")
    private String email;
}
