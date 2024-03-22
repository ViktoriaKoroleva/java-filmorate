package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import javax.validation.Valid;
import java.util.Collection;
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
    public Collection<User> getUsers() {
        return users.values();
    }

    @GetMapping
    public List<User> findAll() {
        log.info("Количество пользователей: {}", users.size());
        return List.copyOf(users.values());
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) throws ValidationException {

        validateUsers(user);
        user.setId(generatorId());
        users.put(user.getId(), user);
        log.info("Фильм успешно добавлен: {}", user);

        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
        validateUsers(user);
        if (!users.containsKey(user.getId())) {
            users.replace(user.getId(), user);
            log.debug("Пользователь успешно обновлен: {}", user);
        }
        users.put(user.getId(), user);
        log.info("Фильм c id" + user.getId() + " обновлён");
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