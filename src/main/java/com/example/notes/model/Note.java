package com.example.notes.model;

import com.example.user.models.User;
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
@Table(name = "Notes", schema = "public")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "creator")
    @ToString.Exclude
    private User creator;
    @JoinColumn(name = "header")
    private String header;
    @JoinColumn(name = "created")
    private LocalDateTime created;
    @JoinColumn(name = "updated")
    private LocalDateTime updated;
    @JoinColumn(name = "description")
    private String description;
}
