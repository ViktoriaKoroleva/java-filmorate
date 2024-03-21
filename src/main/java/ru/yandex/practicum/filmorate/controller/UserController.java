package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int userId = 1;

    private int generatorId() {
        return userId++;
    }

    @GetMapping
    public List<User> findAll() {
        log.info("Количество пользователей: {}", users.size());
        return List.copyOf(users.values());
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new ValidationException("Имя пользователя не может быть пустым");
        }
        for (User registeredUser : users.values()) {

            if (registeredUser.getEmail().equals(user.getEmail())) {

                log.warn("Пользователь с электронной почтой " + user.getEmail()
                        + " уже зарегистрирован");
                throw new ValidationException("Пользователь с электронной почтой уже зарегистрирован");
            }
        }
        validateUsers(user);
        user.setId(generatorId());
        users.put(user.getId(), user);
        log.debug("Пользователь новый добавлен: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        validateUsers(user);
        if (users.get(user.getId()) != null) {
            users.replace(user.getId(), user);
            log.debug("Пользователь успешно обновлен: {}", user);
        } else {
            log.debug("Такого пользователя не существует");
            throw new ValidationException("Такого пользователя не существует");
        }
        return user;
    }

    public User validateUsers(User user) throws ValidationException {
        if (user.getName() == null || user.getName().isBlank()) {
            log.info("Установлен логин вместо имени пользователя");
            user.setName(user.getLogin());
        }
        return user;
    }
}