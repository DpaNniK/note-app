package com.example.user.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "Users", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "name")
    private String name;
    @JoinColumn(name = "surname")
    private String surname;
    @JoinColumn(name = "email")
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @JoinColumn(name = "password")
    private String password;
}
