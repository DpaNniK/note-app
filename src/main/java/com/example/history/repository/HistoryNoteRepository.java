package com.example.history.repository;

import com.example.history.model.HistoryNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryNoteRepository extends JpaRepository<HistoryNote, Integer> {

    void deleteAllByNoteId(Integer noteId);

    List<HistoryNote> getHistoryNotesByNoteIdOrderByUpdatedDesc(Integer noteId);
}
