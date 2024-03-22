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
    private final Map<Integer, User> userHashMap = new HashMap<>();
    private int userId = 1;

    private int generatorId() {
        return userId++;
    }

    @GetMapping
    public List<User> findAll() {
        log.info("Количество пользователей: {}", userHashMap.size());
        return List.copyOf(userHashMap.values());
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) throws ValidationException {

        if (userHashMap.containsValue(user)) {
            log.info("Пользователь уже существует");
            throw new ValidationException("Пользователь уже существует");
        }

        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(generatorId());
        userHashMap.put(user.getId(), user);
        log.info("Пользователь успешно добавлен: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        if (userHashMap.containsKey(user.getId())) {
            userHashMap.replace(user.getId(), user);
            log.debug("Пользователь успешно обновлен: {}", user);
            log.info("Пользователь c id " + user.getId() + " обновлён");
            return user;
        } else {
            throw new ValidationException("Пользователь с id " + user.getId() + " не существует");
        }
    }

}