package com.example.notes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для обновления заметки")
public class UpdateNoteDto {
    @Schema(description = "Заголовок заметки", example = "newHeader")
    private String header;
    @Schema(description = "Описание заметки", example = "newDesc")
    private String description;
}
