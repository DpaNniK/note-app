package com.example.user.dto;

import com.example.user.models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAdminUpdateDto {
    private String name;
    private String surname;
    private String password;
    private String email;
    private Role role;
}
