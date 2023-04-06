package com.example;

import com.example.error.RequestError;
import com.example.user.dto.UserAdminUpdateDto;
import com.example.user.dto.UserDto;
import com.example.user.dto.UserResultDto;
import com.example.user.dto.UserUpdateDto;
import com.example.user.models.Role;
import com.example.user.models.User;
import com.example.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserService userService;
    private UserDto userDto;
    private UserAdminUpdateDto userAdminUpdateDto;
    private UserUpdateDto userUpdateDto;

    @BeforeEach
    void setValues() {
        this.userDto = new UserDto("Иван", "Петров",
                "123456", "ivan@mail.ru");
        this.userAdminUpdateDto = new UserAdminUpdateDto("Илья", "Цой",
                "123789", "newEmail@mail.ru", Role.ADMIN);
        this.userUpdateDto = new UserUpdateDto();
        userUpdateDto.setEmail("newUserEmail@gmail.com");
    }

    @AfterEach
    void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    public void testCreateUserByAdmin() {
        UserResultDto resultUser = userService.createNewUser(userDto);

        assertEquals(resultUser.getId().getClass(), Integer.class,
                "Неверный тип переменной ID");
        assertEquals(resultUser.getName(), userDto.getName(),
                "Неверно присвоено имя пользователя");
        assertEquals(resultUser.getSurname(), userDto.getSurname(),
                "Неверно присвоена фамилия пользователя");
        assertEquals(resultUser.getEmail(), userDto.getEmail(),
                "Неверно присвоен email пользователя");
    }

    @Test
    public void testChangeUserByAdmin() {
        UserResultDto user = userService.createNewUser(userDto);
        UserResultDto updatedUser = userService.changeUser(user.getId(), userAdminUpdateDto);

        assertEquals(updatedUser.getId(), user.getId(),
                "Неверный ID после изменения профиля");
        assertEquals(updatedUser.getName(), userAdminUpdateDto.getName(),
                "Неверно присвоено имя пользователя");
        assertEquals(updatedUser.getSurname(), userAdminUpdateDto.getSurname(),
                "Неверно присвоена фамилия пользователя");
        assertEquals(updatedUser.getEmail(), userAdminUpdateDto.getEmail(),
                "Неверно присвоен email пользователя");
    }

    //Проверка, если пользователь поменял только email
    @Test
    public void testChangeProfileByUser() {
        UserResultDto user = userService.createNewUser(userDto);
        UserResultDto updatedUser = userService.changeProfile(user.getEmail(), userUpdateDto);

        assertEquals(updatedUser.getId(), user.getId(),
                "Неверный ID после изменения профиля");
        assertEquals(updatedUser.getName(), user.getName(),
                "Неверно присвоено имя пользователя");
        assertEquals(updatedUser.getSurname(), user.getSurname(),
                "Неверно присвоена фамилия пользователя");
        assertEquals(updatedUser.getEmail(), userUpdateDto.getEmail(),
                "Неверно присвоен email пользователя");
    }

    @Test
    public void testGetUserByEmail() {
        UserResultDto user = userService.createNewUser(userDto);
        User resultUser = userService.getUserByEmail(user.getEmail());

        assertEquals(resultUser.getId(), user.getId(),
                "Неверный ID после изменения профиля");
        assertEquals(resultUser.getName(), user.getName(),
                "Неверно присвоено имя пользователя");
        assertEquals(resultUser.getSurname(), user.getSurname(),
                "Неверно присвоена фамилия пользователя");
        assertEquals(resultUser.getEmail(), user.getEmail(),
                "Неверно присвоен email пользователя");
    }

    @Test
    public void get404NotFoundUserAfterDelete() {
        RequestError er = Assertions.assertThrows(
                RequestError.class,
                getErrorNotFoundUserAfterDelete()
        );
        assertEquals(HttpStatus.NOT_FOUND, er.getStatus());
    }

    //Получаю конфликт, если при изменении профиля новая почта пользователя уже занята
    @Test
    public void get409ConflictEmailBusy() {
        RequestError er = Assertions.assertThrows(
                RequestError.class,
                getErrorConflictEmailBusy()
        );
        assertEquals(HttpStatus.CONFLICT, er.getStatus());
    }

    @Test
    public void get409ConflictCreateUserWithEmailBusy() {
        RequestError er = Assertions.assertThrows(
                RequestError.class,
                getErrorConflictCreateUserEmailBusy()
        );
        assertEquals(HttpStatus.CONFLICT, er.getStatus());
    }

    private Executable getErrorNotFoundUserAfterDelete() {
        return () -> {
            UserResultDto user = userService.createNewUser(userDto);
            userService.deleteUser(user.getId());
            userService.getUserByEmail(user.getEmail());
        };
    }

    private Executable getErrorConflictEmailBusy() {
        return () -> {
            UserResultDto user = userService.createNewUser(userDto);
            userDto.setEmail("busyMail@mail.ru");
            userUpdateDto.setEmail(userDto.getEmail());
            userService.createNewUser(userDto);
            userService.changeProfile(user.getEmail(), userUpdateDto);
        };
    }

    private Executable getErrorConflictCreateUserEmailBusy() {
        return () -> {
            userService.createNewUser(userDto);
            userService.createNewUser(userDto);
        };
    }
}
