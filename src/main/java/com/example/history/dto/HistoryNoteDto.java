package com.example.history.dto;

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
@Schema(description = "DTO для истории изменений заметки")
public class HistoryNoteDto {
    @Schema(description = "Заголовок заметки", example = "oldHead")
    private String header;
    @Schema(description = "Описание заметки", example = "oldDesc")
    private String description;
    @Schema(description = "Время создания заметки", example = "2023-06-12 07:15:05")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    @Schema(description = "Время изменения заметки", example = "2023-09-05 12:11:32")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;
}
