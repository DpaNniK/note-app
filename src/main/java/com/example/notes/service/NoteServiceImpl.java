package com.example.notes.service;

import com.example.error.RequestError;
import com.example.history.dto.HistoryNoteDto;
import com.example.history.service.HistoryNoteService;
import com.example.notes.dto.NoteDto;
import com.example.notes.dto.ResultNoteDto;
import com.example.notes.dto.UpdateNoteDto;
import com.example.notes.mapper.NoteMapper;
import com.example.notes.model.Note;
import com.example.notes.repository.NoteRepository;
import com.example.user.models.User;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserService userService;
    private final HistoryNoteService historyNoteService;

    @Override
    public ResultNoteDto createNewNote(String email, NoteDto noteDto) {
        User creator = userService.getUserByEmail(email);
        Note note = NoteMapper.toNote(noteDto, creator, LocalDateTime.now(),
                LocalDateTime.now());
        Note resultNote = noteRepository.save(note);
        log.info("Пользователь {} создал заметку {}", creator, resultNote);
        return NoteMapper.toResultNoteDto(note);
    }

    @Override
    public ResultNoteDto updateNote(String email, Integer noteId, UpdateNoteDto updateNoteDto) {
        User creator = userService.getUserByEmail(email);
        Note note = getNoteById(noteId);
        if (!note.getCreator().equals(creator)) {
            log.info("Невозможно изменить заметку. " +
                    "Пользователь {} не является создателем заметки {}", creator, note);
        }
        historyNoteService.saveHistory(note);
        if (updateNoteDto.getHeader() != null) {
            log.info("Пользователь {} изменил заголовок заметки {}", creator, note);
            note.setHeader(updateNoteDto.getHeader());
        }
        if (updateNoteDto.getDescription() != null) {
            log.info("Пользователь {} изменил описание заметки {}", creator, note);
            note.setDescription(updateNoteDto.getDescription());
        }
        note.setUpdated(LocalDateTime.now());
        Note resultNote = noteRepository.save(note);
        log.info("Обновлена информация в заметке {}", note);
        return NoteMapper.toResultNoteDto(resultNote);
    }

    @Override
    public Note getNoteById(Integer noteId) {
        Note note = noteRepository.findNoteById(noteId);
        if (note == null) {
            log.info("Заметки под id = {} не существует", noteId);
            throw new RequestError(HttpStatus.NOT_FOUND, "Заметки под id = " + noteId +
                    " не найдено");
        }
        return note;
    }

    @Override
    public void deleteNoteById(String email, Integer noteId) {
        User creator = userService.getUserByEmail(email);
        Note note = getNoteById(noteId);
        if (!note.getCreator().equals(creator)) {
            log.info("Невозможно удалить заметку. " +
                    "Пользователь {} не является создателем заметки {}", creator, note);
            throw new RequestError(HttpStatus.CONFLICT, "Пользователь " + creator +
                    " не является создателем заметки " + note);
        }
        historyNoteService.deleteHistory(noteId);
        noteRepository.deleteNotById(noteId);
        log.info("Заметка {} удалена", note);
    }

    @Override
    public Collection<HistoryNoteDto> getHistoryForNote(String email, Integer noteId) {
        User creator = userService.getUserByEmail(email);
        Note note = getNoteById(noteId);
        if (!note.getCreator().equals(creator)) {
            log.info("Невозможно получить историю заметки. " +
                    "Пользователь {} не является создателем заметки {}", creator, note);
        }
        log.info("Запрошена история заметки {}", note);
        return historyNoteService.getHistoryForNote(noteId);
    }

    @Override
    public Collection<ResultNoteDto> getAllNotesForUser(String email) {
        User creator = userService.getUserByEmail(email);
        List<ResultNoteDto> resultNotes = new ArrayList<>();
        List<Note> notes = noteRepository.getAllByCreator(creator);
        notes.forEach(note -> resultNotes.add(NoteMapper.toResultNoteDto(note)));
        log.info("Возвращен список заметок пользователя {}", creator);
        return resultNotes;
    }
}
