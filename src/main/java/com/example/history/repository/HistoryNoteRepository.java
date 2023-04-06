package com.example.history.repository;

import com.example.history.model.HistoryNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public interface HistoryNoteRepository extends JpaRepository<HistoryNote, Integer> {

    @Modifying
    @Transactional
    @Query("delete from HistoryNote h where h.noteId = :noteId")
    void deleteHistoryByNoteId(@Param("noteId") Integer noteId);

    List<HistoryNote> getHistoryNotesByNoteIdOrderByUpdatedDesc(Integer noteId);

    @Modifying
    @Transactional
    @Query("delete from HistoryNote h where h.noteId in :noteIds")
    void deleteAllNotesByNoteIds(@Param("noteIds") Collection<Integer> noteIds);
}
