package com.example.history.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "history_notes", schema = "public")
public class HistoryNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "note_id")
    private Integer noteId;
    @JoinColumn(name = "created")
    private LocalDateTime created;
    @JoinColumn(name = "updated")
    private LocalDateTime updated;
    @JoinColumn(name = "description")
    private String description;
    @JoinColumn(name = "header")
    private String header;
}
