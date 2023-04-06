package com.example.controllers;

import com.example.history.dto.HistoryNoteDto;
import com.example.notes.controller.NoteController;
import com.example.notes.dto.NoteDto;
import com.example.notes.dto.ResultNoteDto;
import com.example.notes.dto.UpdateNoteDto;
import com.example.notes.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteControllerTest {
    @Mock
    private NoteService noteService;
    @Mock
    private Principal principal = () -> "null";
    @InjectMocks
    private NoteController noteController;
    private ResultNoteDto resultNoteDto;
    private NoteDto noteDto;
    private UpdateNoteDto updateNoteDto;
    private HistoryNoteDto historyNoteDto;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.noteDto = new NoteDto();
        noteDto.setHeader("Head");
        noteDto.setDescription("Desc");
        this.resultNoteDto = new ResultNoteDto();
        resultNoteDto.setHeader(noteDto.getHeader());
        resultNoteDto.setDescription(noteDto.getDescription());
        this.updateNoteDto = new UpdateNoteDto();
        updateNoteDto.setHeader(noteDto.getHeader());
        updateNoteDto.setDescription(noteDto.getDescription());
        this.historyNoteDto = new HistoryNoteDto();
        historyNoteDto.setHeader(noteDto.getHeader());
        historyNoteDto.setDescription(noteDto.getDescription());
    }

    @Test
    public void createNewNoteTest() {
        when(principal.getName()).thenReturn("testname@mail.ru");
        when(noteService.createNewNote(any(), any())).thenReturn(resultNoteDto);

        ResultNoteDto resultNote = noteController
                .createNewNote(principal, noteDto);

        Optional<ResultNoteDto> resultOptional = Optional.ofNullable(resultNote);

        assertThat(resultOptional)
                .isPresent()
                .hasValueSatisfying(resultNoteDto ->
                        assertThat(resultNoteDto).hasFieldOrPropertyWithValue("header", noteDto.getHeader())
                                .hasFieldOrPropertyWithValue("description", noteDto.getDescription())
                );
    }

    @Test
    public void updateNoteTest() {
        when(principal.getName()).thenReturn("testname@mail.ru");
        when(noteService.updateNote(any(), any(), any())).thenReturn(resultNoteDto);

        ResultNoteDto resultNote = noteController
                .updateNote(principal, 1, updateNoteDto);

        Optional<ResultNoteDto> resultOptional = Optional.ofNullable(resultNote);

        assertThat(resultOptional)
                .isPresent()
                .hasValueSatisfying(resultNoteDto ->
                        assertThat(resultNoteDto).hasFieldOrPropertyWithValue("header", noteDto.getHeader())
                                .hasFieldOrPropertyWithValue("description", noteDto.getDescription())
                );
    }

    @Test
    public void getHistoryNoteTest() {
        Collection<HistoryNoteDto> historyNotes = List.of(historyNoteDto);
        when(principal.getName()).thenReturn("testname@mail.ru");
        when(noteService.getHistoryForNote(any(), any())).thenReturn(historyNotes);

        Collection<HistoryNoteDto> resultNotes = noteController
                .getHistoryForNote(principal, 1);

        assertEquals(resultNotes.size(), 1, "Неверно получен список");
    }

    @Test
    public void getAllNotesTest() {
        Collection<ResultNoteDto> notes = List.of(resultNoteDto);
        when(principal.getName()).thenReturn("testname@mail.ru");
        when(noteService.getAllNotesForUser(any())).thenReturn(notes);

        Collection<ResultNoteDto> resultNotes = noteController.getAllNotesForUser(principal);

        assertEquals(resultNotes.size(), 1, "Неверно получен список");
    }
}
