package com.example.notes.controller;

import com.example.history.dto.HistoryNoteDto;
import com.example.notes.dto.NoteDto;
import com.example.notes.dto.ResultNoteDto;
import com.example.notes.dto.UpdateNoteDto;
import com.example.notes.service.NoteService;
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
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultNoteDto createNewNote(Principal principal,
                                       @RequestBody @Valid NoteDto noteDto) {
        return noteService.createNewNote(principal.getName(), noteDto);
    }

    @PatchMapping("/{noteId}")
    public ResultNoteDto updateNote(Principal principal,
                                    @PathVariable Integer noteId,
                                    @RequestBody UpdateNoteDto updateNoteDto) {
        return noteService.updateNote(principal.getName(), noteId, updateNoteDto);
    }

    //Получение истории изменений заметки.
    //История отсортирована в порядке убывания параметра updated
    @GetMapping("/{noteId}/history")
    public Collection<HistoryNoteDto> getHistoryForNote(Principal principal,
                                                        @PathVariable Integer noteId) {
        return noteService.getHistoryForNote(principal.getName(), noteId);
    }

    //Получение списка всех заметок пользователя
    @GetMapping("/all")
    public Collection<ResultNoteDto> getAllNotesForUser(Principal principal) {
        return noteService.getAllNotesForUser(principal.getName());
    }

    //При удалении заметки удаляется все ее история
    @DeleteMapping("/{noteId}")
    public void deleteNote(Principal principal,
                           @PathVariable Integer noteId) {
        noteService.deleteNoteById(principal.getName(), noteId);
    }
}
