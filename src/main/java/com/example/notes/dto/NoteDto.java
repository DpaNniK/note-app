package com.example.notes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для создания заметки")
public class NoteDto {
    @NotBlank
    @Schema(description = "Заголовок заметки", example = "header")
    private String header;
    @NotBlank
    @Schema(description = "Описание заметки", example = "desc")
    private String description;
}
