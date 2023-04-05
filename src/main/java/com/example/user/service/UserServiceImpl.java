package com.example.user.service;

import com.example.error.RequestError;
import com.example.user.dto.UserDto;
import com.example.user.dto.UserResultDto;
import com.example.user.dto.UserAdminUpdateDto;
import com.example.user.dto.UserUpdateDto;
import com.example.user.mapper.UserMapper;
import com.example.user.models.Role;
import com.example.user.models.User;
import com.example.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserResultDto createNewUser(UserDto userDto) {
        User checkUser = userRepository.findUserByEmail(userDto.getEmail());
        if (checkUser != null) {
            log.warn("Невозможно создать пользователя {}, почта уже занята", userDto);
            throw new RequestError(HttpStatus.CONFLICT
                    , "Пользователь с почтой " + userDto.getEmail() + "  уже существует");
        }
        User user = UserMapper.toUser(userDto);
        user.setRole(Role.USER);
        User resultUser = userRepository.save(user);
        log.info("Создан новый пользователь - " + resultUser);
        return UserMapper.toUserResultDto(resultUser);
    }

    @Override
    public UserResultDto changeUser(Integer userId, UserAdminUpdateDto userUpdateDto) {
        User user = getUserById(userId);
        log.info("Запрос на изменение пользователя {}", user);
        if (userUpdateDto.getName() != null) {
            log.info("Имя пользователя {} изменено на {} администратором",
                    user, userUpdateDto.getName());
            user.setName(userUpdateDto.getName());
        }
        if (userUpdateDto.getSurname() != null) {
            log.info("Фамилия пользователя {} изменена на {} администратором",
                    user, userUpdateDto.getSurname());
            user.setSurname(userUpdateDto.getSurname());
        }
        if (userUpdateDto.getEmail() != null) {
            log.info("Email пользователя {} изменен на {} администратором",
                    user, userUpdateDto.getEmail());
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getRole() != null && userUpdateDto.getRole() != user.getRole()) {
            log.info("Роль пользователя {} изменена на {} администратором",
                    user, userUpdateDto.getRole());
            user.setRole(userUpdateDto.getRole());
        }
        if (userUpdateDto.getPassword() != null) {
            log.info("Пароль пользователя {} изменен на {} администратором",
                    user, userUpdateDto.getPassword());
            user.setPassword(userUpdateDto.getPassword());
        }
        User resultUser = userRepository.save(user);
        log.info("Данные пользователя {} обновлены", resultUser);
        return UserMapper.toUserResultDto(resultUser);
    }

    @Override
    public UserResultDto changeProfile(String email, UserUpdateDto userUpdateDto) {
        User oldUser = userRepository.findUserByEmail(email);
        if (oldUser == null) {
            log.warn("Пользователь с почтой {} не найден", email);
            throw new RequestError(HttpStatus.CONFLICT
                    , "Пользователь с почтой " + email + " не найден");
        }
        if (userUpdateDto.getName() != null) {
            log.info("Пользователь {} изменил имя на {}", oldUser, userUpdateDto.getName());
            oldUser.setName(userUpdateDto.getName());
        }
        if (userUpdateDto.getSurname() != null) {
            log.info("Пользователь {} изменил фамилию на {}", oldUser, userUpdateDto.getSurname());
            oldUser.setSurname(userUpdateDto.getSurname());
        }
        if (userUpdateDto.getEmail() != null) {
            if (userRepository.findUserByEmail(userUpdateDto.getEmail()) != null) {
                throw new RequestError(HttpStatus.CONFLICT,
                        "Невозможно изменить почту. Почта" + userUpdateDto.getEmail() + " уже занята");
            }
            log.info("Пользователь {} изменил почту на {}", oldUser, userUpdateDto.getEmail());
            oldUser.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getPassword() != null) {
            log.info("Пользователь {} изменил пароль на {}", oldUser, userUpdateDto.getPassword());
            oldUser.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }
        User resultUser = userRepository.save(oldUser);
        log.info("Информация о пользователе {} обновлена", resultUser);
        return UserMapper.toUserResultDto(resultUser);
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            log.warn("Пользователь с почтой {} не найден", email);
            throw new RequestError(HttpStatus.CONFLICT,
                    "Пользователь с почтой " + email + " не найден");
        }
        return user;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = getUserById(userId);
        userRepository.deleteById(userId);
        log.info("Пользователь {} удален", user);
    }

    private User getUserById(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.info("Пользователь с id = {} не найден", userId);
            throw new RequestError(HttpStatus.NOT_FOUND,
                    "Пользователь под id - " + userId + " не найден");
        }
        log.info("Получен пользователь {}", user);
        return user;
    }
}
