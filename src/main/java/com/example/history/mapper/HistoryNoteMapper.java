package com.example.history.mapper;

import com.example.history.dto.HistoryNoteDto;
import com.example.history.model.HistoryNote;
import com.example.notes.model.Note;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HistoryNoteMapper {

    public static HistoryNote toHistoryNote(Note note) {
        HistoryNote historyNote = new HistoryNote();
        historyNote.setHeader(note.getHeader());
        historyNote.setDescription(note.getDescription());
        historyNote.setUpdated(note.getUpdated());
        historyNote.setCreated(note.getCreated());
        historyNote.setNoteId(note.getId());
        return historyNote;
    }

    public static HistoryNoteDto toHistoryNoteDto(HistoryNote historyNote) {
        HistoryNoteDto historyNoteDto = new HistoryNoteDto();
        historyNoteDto.setCreated(historyNote.getCreated());
        historyNoteDto.setUpdated(historyNote.getUpdated());
        historyNoteDto.setDescription(historyNote.getDescription());
        historyNoteDto.setHeader(historyNote.getHeader());
        return historyNoteDto;
    }
}
