package com.example.notes.repository;

import com.example.notes.model.Note;
import com.example.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {

    Note findNoteById(Integer id);

    List<Note> getAllByCreator(User creator);

    @Query("select n.id from Note n where n.creator.email = :email")
    List<Integer> getAllIds(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("delete from Note n where n.id = :id")
    void deleteNotById(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("delete from Note n where n.creator = :creator")
    void deleteAllNoteByCreator(@Param("creator") User creator);
}
