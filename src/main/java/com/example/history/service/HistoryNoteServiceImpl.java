package com.example.history.service;

import com.example.history.dto.HistoryNoteDto;
import com.example.history.mapper.HistoryNoteMapper;
import com.example.history.model.HistoryNote;
import com.example.history.repository.HistoryNoteRepository;
import com.example.notes.model.Note;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryNoteServiceImpl implements HistoryNoteService {

    private final HistoryNoteRepository historyNoteRepository;

    @Override
    public void saveHistory(Note note) {
        HistoryNote historyNote = HistoryNoteMapper.toHistoryNote(note);
        historyNoteRepository.save(historyNote);
        log.info("Заметка {} добавлена в историю", note);
    }

    @Override
    public void deleteHistory(Integer noteId) {
        historyNoteRepository.deleteHistoryByNoteId(noteId);
        log.info("Очищена история заметки под id = {}", noteId);
    }

    @Override
    public void deleteHistoryByNoteIds(Collection<Integer> ids) {
        historyNoteRepository.deleteAllNotesByNoteIds(ids);
        log.info("Очищена история заметок из списка - {}", ids);
    }

    @Override
    public Collection<HistoryNoteDto> getHistoryForNote(Integer noteId) {
        List<HistoryNoteDto> historyNotesDto = new ArrayList<>();
        List<HistoryNote> historyNotes = historyNoteRepository
                .getHistoryNotesByNoteIdOrderByUpdatedDesc(noteId);
        historyNotes.forEach(historyNote -> historyNotesDto
                .add(HistoryNoteMapper.toHistoryNoteDto(historyNote)));
        log.info("Получена история заметки с id = {}", noteId);
        return historyNotesDto;
    }
}
