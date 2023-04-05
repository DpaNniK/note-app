package com.example.history.service;

import com.example.history.dto.HistoryNoteDto;
import com.example.notes.model.Note;

import java.util.Collection;

public interface HistoryNoteService {

    void saveHistory(Note note);

    void deleteHistory(Integer noteId);

    Collection<HistoryNoteDto> getHistoryForNote(Integer noteId);
}
