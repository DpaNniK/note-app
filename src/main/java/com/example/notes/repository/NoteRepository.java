package com.example.notes.repository;

import com.example.notes.model.Note;
import com.example.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {

    Note findNoteById(Integer id);

    List<Note> getAllByCreator(User creator);
}
