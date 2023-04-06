package com.example.controllers;

import com.example.notes.service.NoteService;
import com.example.user.controllers.AdminController;
import com.example.user.dto.UserAdminUpdateDto;
import com.example.user.dto.UserDto;
import com.example.user.dto.UserResultDto;
import com.example.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private NoteService noteService;
    @InjectMocks
    private AdminController adminController;
    private final ObjectMapper mapper = new ObjectMapper();
    private MockMvc mvc;
    private UserDto userDto;
    private UserResultDto userResultDto;
    private UserAdminUpdateDto userAdminUpdateDto;

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders
                .standaloneSetup(adminController)
                .build();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        this.userDto = new UserDto();
        userDto.setName("testName");
        userDto.setSurname("testSurname");
        userDto.setEmail("test@mail.ru");
        userDto.setPassword("123456");
        this.userResultDto = new UserResultDto();
        userResultDto.setEmail(userDto.getEmail());
        userResultDto.setName(userDto.getName());
        userResultDto.setSurname(userDto.getSurname());
        this.userAdminUpdateDto = new UserAdminUpdateDto();
        userAdminUpdateDto.setName(userDto.getName());
        userAdminUpdateDto.setSurname(userDto.getSurname());
        userAdminUpdateDto.setEmail(userDto.getEmail());
    }

    @Test
    public void createNewUserByAdminTest() throws Exception {
        when(userService.createNewUser(any())).thenReturn(userResultDto);

        mvc.perform(post("/admins/create_user")
                        .content(mapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(userDto.getName())))
                .andExpect(jsonPath("$.surname", is(userDto.getSurname())))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())));
    }

    @Test
    public void changeUserByAdminTest() throws Exception {
        when(userService.changeUser(any(), any())).thenReturn(userResultDto);

        mvc.perform(patch("/admins/1")
                        .content(mapper.writeValueAsString(userAdminUpdateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(userDto.getName())))
                .andExpect(jsonPath("$.surname", is(userDto.getSurname())))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())));
    }

    @Test
    public void deleteUserByAdminTest() throws Exception {
        mvc.perform(delete("/admins/1")
                        .content(mapper.writeValueAsString(userAdminUpdateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
