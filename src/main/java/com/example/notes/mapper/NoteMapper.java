package com.example.notes.mapper;

import com.example.notes.dto.NoteDto;
import com.example.notes.dto.ResultNoteDto;
import com.example.notes.model.Note;
import com.example.user.mapper.UserMapper;
import com.example.user.models.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class NoteMapper {
    public static Note toNote(NoteDto noteDto, User creator, LocalDateTime created,
                              LocalDateTime update) {
        Note note = new Note();
        note.setHeader(noteDto.getHeader());
        note.setDescription(noteDto.getDescription());
        note.setCreator(creator);
        note.setCreated(created);
        note.setUpdated(update);
        return note;
    }

    public static ResultNoteDto toResultNoteDto(Note note) {
        ResultNoteDto resultNoteDto = new ResultNoteDto();
        resultNoteDto.setId(note.getId());
        resultNoteDto.setHeader(note.getHeader());
        resultNoteDto.setDescription(note.getDescription());
        resultNoteDto.setCreator(UserMapper.toUserResultDto(note.getCreator()));
        resultNoteDto.setCreated(note.getCreated());
        resultNoteDto.setUpdated(note.getUpdated());
        return resultNoteDto;
    }
}
