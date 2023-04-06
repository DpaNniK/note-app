package com.example;

import com.example.error.RequestError;
import com.example.history.dto.HistoryNoteDto;
import com.example.notes.dto.NoteDto;
import com.example.notes.dto.ResultNoteDto;
import com.example.notes.dto.UpdateNoteDto;
import com.example.notes.model.Note;
import com.example.notes.service.NoteService;
import com.example.user.dto.UserDto;
import com.example.user.dto.UserResultDto;
import com.example.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

//В NoteService используется класс HistoryNoteService, поэтому для него нет отдельного тестового класса,
//его функционал тестируется тут
@SpringBootTest
public class NoteServiceTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;
    private UserDto userDto;
    private NoteDto noteDto;
    private UpdateNoteDto updateNoteDto;

    @BeforeEach
    void setValues() {
        this.userDto = new UserDto("Иван", "Петров",
                "123456", "ivan@mail.ru");
        this.noteDto = new NoteDto("head", "desc");
        this.updateNoteDto = new UpdateNoteDto("newHead", "newDesc");
    }

    @AfterEach
    void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "history_notes", "notes", "users");
    }

    @Test
    public void testCreateNewNote() {
        UserResultDto user = userService.createNewUser(userDto);
        ResultNoteDto note = noteService.createNewNote(user.getEmail(), noteDto);

        assertEquals(note.getCreator().getName(), user.getName(),
                "Неверное имя создателя заметки");
        assertEquals(note.getCreator().getSurname(), user.getSurname(),
                "Неверная фамилия создателя заметки");
        assertEquals(note.getCreator().getEmail(), user.getEmail(),
                "Неверный Email создателя заметки");
        assertEquals(note.getCreator().getId(), user.getId(),
                "Неверный ID создателя заметки");
        assertEquals(note.getHeader(), noteDto.getHeader(), "Неверный заголовок заметки");
        assertEquals(note.getDescription(), noteDto.getDescription(), "Неверное описание замкти");
    }

    @Test
    public void testUpdateNote() {
        UserResultDto user = userService.createNewUser(userDto);
        ResultNoteDto note = noteService.createNewNote(user.getEmail(), noteDto);
        ResultNoteDto updatedNote = noteService.updateNote(user.getEmail(), note.getId(), updateNoteDto);

        assertEquals(updatedNote.getCreator().getName(), user.getName(),
                "Неверное имя создателя заметки");
        assertEquals(updatedNote.getCreator().getSurname(), user.getSurname(),
                "Неверная фамилия создателя заметки");
        assertEquals(updatedNote.getCreator().getEmail(), user.getEmail(),
                "Неверный Email создателя заметки");
        assertEquals(updatedNote.getCreator().getId(), user.getId(),
                "Неверный ID создателя заметки");
        assertEquals(updatedNote.getId(), note.getId(), "Неверный ID заметки");
        assertEquals(updatedNote.getHeader(), updateNoteDto.getHeader(), "Неверный заголовок заметки");
        assertEquals(updatedNote.getDescription(), updateNoteDto.getDescription(), "Неверное описание заметки");
    }

    @Test
    public void testGetNoteById() {
        UserResultDto user = userService.createNewUser(userDto);
        ResultNoteDto note = noteService.createNewNote(user.getEmail(), noteDto);
        Note resultNote = noteService.getNoteById(note.getId());

        assertEquals(resultNote.getCreator().getName(), user.getName(),
                "Неверное имя создателя заметки");
        assertEquals(resultNote.getCreator().getSurname(), user.getSurname(),
                "Неверная фамилия создателя заметки");
        assertEquals(resultNote.getCreator().getEmail(), user.getEmail(),
                "Неверный Email создателя заметки");
        assertEquals(resultNote.getCreator().getId(), user.getId(),
                "Неверный ID создателя заметки");
        assertEquals(resultNote.getHeader(), note.getHeader(), "Неверный заголовок заметки");
        assertEquals(resultNote.getDescription(), note.getDescription(), "Неверное описание заметки");
    }

    @Test
    public void testGetHistoryNote() {
        UserResultDto user = userService.createNewUser(userDto);
        ResultNoteDto note = noteService.createNewNote(user.getEmail(), noteDto);
        noteService.updateNote(user.getEmail(), note.getId(), updateNoteDto);
        updateNoteDto.setHeader("newHeaderTwo");
        noteService.updateNote(user.getEmail(), note.getId(), updateNoteDto);

        Collection<HistoryNoteDto> history = noteService
                .getHistoryForNote(user.getEmail(), note.getId());
        assertEquals(history.size(), 2, "Неверное сохранение истории");
    }

    @Test
    public void getAllNotesForUser() {
        UserResultDto user = userService.createNewUser(userDto);
        noteService.createNewNote(user.getEmail(), noteDto);
        noteDto.setHeader("newNoteHeader");
        noteService.createNewNote(user.getEmail(), noteDto);

        Collection<ResultNoteDto> notes = noteService.getAllNotesForUser(user.getEmail());
        assertEquals(notes.size(), 2, "Неверный список заметок");
    }

    //При удалении пользователя должны удаляться и его заметки (+ их история)
    @Test
    public void testDeleteNoteWithUser() {
        RequestError er = Assertions.assertThrows(
                RequestError.class,
                getNotFoundAfterRemoveNote()
        );
        assertEquals(HttpStatus.NOT_FOUND, er.getStatus());
    }

    @Test
    public void get404NotFoundAfterDeleteNote() {
        RequestError er = Assertions.assertThrows(
                RequestError.class,
                getErrorNotFoundAfterDeleteNote()
        );
        assertEquals(HttpStatus.NOT_FOUND, er.getStatus());
    }

    //Попытка пользователя изменить чужую заметку
    @Test
    public void get409ConflictUpdatedUserNotCreator() {
        RequestError er = Assertions.assertThrows(
                RequestError.class,
                getErrorConflictUserNotCreatorNote()
        );
        assertEquals(HttpStatus.CONFLICT, er.getStatus());
    }

    //Попытка удалить чужую заметку
    @Test
    public void get409ConflictDeleteUserNotCreator() {
        RequestError er = Assertions.assertThrows(
                RequestError.class,
                getErrorConflictUserNotCreator()
        );
        assertEquals(HttpStatus.CONFLICT, er.getStatus());
    }

    private Executable getErrorConflictUserNotCreatorNote() {
        return () -> {
            UserResultDto user = userService.createNewUser(userDto);
            ResultNoteDto note = noteService.createNewNote(user.getEmail(), noteDto);
            userDto.setEmail("newUserEmail@mail.ru");
            UserResultDto newUser = userService.createNewUser(userDto);
            noteService.updateNote(newUser.getEmail(), note.getId(), updateNoteDto);
        };
    }

    private Executable getErrorNotFoundAfterDeleteNote() {
        return () -> {
            UserResultDto user = userService.createNewUser(userDto);
            ResultNoteDto note = noteService.createNewNote(user.getEmail(), noteDto);
            noteService.deleteNoteById(user.getEmail(), note.getId());
            noteService.getNoteById(note.getId());
        };
    }

    private Executable getErrorConflictUserNotCreator() {
        return () -> {
            UserResultDto user = userService.createNewUser(userDto);
            ResultNoteDto note = noteService.createNewNote(user.getEmail(), noteDto);
            userDto.setEmail("newUserEmail@mail.ru");
            UserResultDto newUser = userService.createNewUser(userDto);
            noteService.deleteNoteById(newUser.getEmail(), note.getId());
        };
    }

    private Executable getNotFoundAfterRemoveNote() {
        return () -> {
            UserResultDto user = userService.createNewUser(userDto);
            ResultNoteDto note = noteService.createNewNote(user.getEmail(), noteDto);
            noteService.updateNote(user.getEmail(), note.getId(), updateNoteDto);
            updateNoteDto.setHeader("newHeader");
            noteService.updateNote(user.getEmail(), note.getId(), updateNoteDto);
            noteDto.setHeader("twoHeader");
            ResultNoteDto noteTwo = noteService.createNewNote(user.getEmail(), noteDto);
            updateNoteDto.setHeader("newTwoHeader");
            noteService.updateNote(user.getEmail(), noteTwo.getId(), updateNoteDto);
            noteService.deleteAllNotesUser(user.getId());
            noteService.getNoteById(note.getId());
        };
    }
}
