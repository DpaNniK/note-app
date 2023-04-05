package com.example.notes.controller;

import com.example.history.dto.HistoryNoteDto;
import com.example.notes.dto.NoteDto;
import com.example.notes.dto.ResultNoteDto;
import com.example.notes.dto.UpdateNoteDto;
import com.example.notes.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/users/notes")
@PreAuthorize("hasAuthority('access:users')")
@Tag(name = "Заметки", description = "Эндпоинты для работы с заметками")
@SecurityRequirement(name = "NoteAPISecureScheme")
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создание новой заметки",
            description = "Позволяет пользователю создать новою заметку"
    )
    public ResultNoteDto createNewNote(Principal principal,
                                       @RequestBody @Valid NoteDto noteDto) {
        return noteService.createNewNote(principal.getName(), noteDto);
    }

    @PatchMapping("/{noteId}")
    @Operation(
            summary = "Изменение заметки",
            description = "Позволяет изменить существующую заметку пользователя"
    )
    public ResultNoteDto updateNote(Principal principal,
                                    @PathVariable Integer noteId,
                                    @RequestBody UpdateNoteDto updateNoteDto) {
        return noteService.updateNote(principal.getName(), noteId, updateNoteDto);
    }

    //Получение истории изменений заметки.
    //История отсортирована в порядке убывания параметра updated
    @GetMapping("/{noteId}/history")
    @Operation(
            summary = "Получение истории изменений заметки",
            description = "Позволяет получить всю историю редактирования заметки"
    )
    public Collection<HistoryNoteDto> getHistoryForNote(Principal principal,
                                                        @PathVariable Integer noteId) {
        return noteService.getHistoryForNote(principal.getName(), noteId);
    }

    //Получение списка всех заметок пользователя
    @GetMapping("/all")
    @Operation(
            summary = "Получений всех заметок пользователя",
            description = "Позволяет получить все заметки, созданные пользователем"
    )
    public Collection<ResultNoteDto> getAllNotesForUser(Principal principal) {
        return noteService.getAllNotesForUser(principal.getName());
    }

    //При удалении заметки удаляется все ее история
    @DeleteMapping("/{noteId}")
    @Operation(
            summary = "Удаление заметки пользователя",
            description = "Позволяет удалить заметку с переданным noteId"
    )
    public void deleteNote(Principal principal,
                           @PathVariable Integer noteId) {
        noteService.deleteNoteById(principal.getName(), noteId);
    }
}
