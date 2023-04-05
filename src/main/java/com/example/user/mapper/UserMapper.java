package com.example.user.mapper;

import com.example.user.dto.UserDto;
import com.example.user.dto.UserResultDto;
import com.example.user.models.User;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@UtilityClass
public class UserMapper {

    //Кодер используется для шифровки пароля,
    //т.о. хранение пароля в бд становится более безопасным
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return user;
    }

    public static UserResultDto toUserResultDto(User user) {
        UserResultDto userResultDto = new UserResultDto();
        userResultDto.setId(user.getId());
        userResultDto.setName(user.getName());
        userResultDto.setSurname(user.getSurname());
        userResultDto.setEmail(user.getEmail());
        return userResultDto;
    }
}
