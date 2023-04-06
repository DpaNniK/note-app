package com.example.controllers;

import com.example.user.controllers.UserController;
import com.example.user.dto.UserResultDto;
import com.example.user.dto.UserUpdateDto;
import com.example.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    private UserUpdateDto userUpdateDto;
    private UserResultDto userResultDto;
    @Mock
    private Principal principal;

    @BeforeEach
    void setUp() {
        this.userUpdateDto = new UserUpdateDto();
        userUpdateDto.setName("testName");
        userUpdateDto.setSurname("testSurname");
        userUpdateDto.setEmail("test@mail.ru");
        userUpdateDto.setPassword("123456");
        this.userResultDto = new UserResultDto();
        userResultDto.setEmail(userUpdateDto.getEmail());
        userResultDto.setName(userUpdateDto.getName());
        userResultDto.setSurname(userUpdateDto.getSurname());
    }

    @Test
    public void changeProfileForUserTest() {
        when(principal.getName()).thenReturn("testname@mail.ru");
        when(userService.changeProfile(any(), any())).thenReturn(userResultDto);

        UserResultDto userResultDtoCreated = userController
                .changeProfile(userUpdateDto, principal);

        Optional<UserResultDto> resultOptional = Optional.ofNullable(userResultDtoCreated);

        assertThat(resultOptional)
                .isPresent()
                .hasValueSatisfying(userResultDto ->
                        assertThat(userResultDto).hasFieldOrPropertyWithValue("name", userUpdateDto.getName())
                                .hasFieldOrPropertyWithValue("email", userUpdateDto.getEmail())
                                .hasFieldOrPropertyWithValue("surname", userUpdateDto.getSurname())
                );
    }
}
