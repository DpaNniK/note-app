package com.example.notes.service;

import com.example.history.dto.HistoryNoteDto;
import com.example.notes.dto.NoteDto;
import com.example.notes.dto.ResultNoteDto;
import com.example.notes.dto.UpdateNoteDto;
import com.example.notes.model.Note;

import java.util.Collection;

public interface NoteService {

    ResultNoteDto createNewNote(String email, NoteDto noteDto);

    ResultNoteDto updateNote(String email, Integer noteId, UpdateNoteDto updateNoteDto);

    Note getNoteById(Integer noteId);

    void deleteNoteById(String email, Integer noteId);

    void deleteAllNotesUser(Integer userId);

    Collection<HistoryNoteDto> getHistoryForNote(String email, Integer noteId);

    Collection<ResultNoteDto> getAllNotesForUser(String email);

}
