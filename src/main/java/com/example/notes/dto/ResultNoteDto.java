package com.example.notes.dto;

import com.example.user.dto.UserResultDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для результата создания заметки")
public class ResultNoteDto {
    @Schema(description = "ID заметки")
    private Integer id;
    @Schema(description = "Создатель заметки")
    private UserResultDto creator;
    @Schema(description = "Заголовок заметки", example = "head")
    private String header;
    @Schema(description = "Описание заметки", example = "desc")
    private String description;
    @Schema(description = "Время создания заметки", example = "2023-02-12 03:10:05")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    @Schema(description = "Время обновления заметки", example = "2023-04-12 07:15:05")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;
}
