package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @Validated
    public User createUser(@Valid @RequestBody User user) {
        User newUser = userService.createUser(user);
        log.debug("Добавлен пользователь");
        return newUser;
    }

    @PutMapping
    @Validated
    public User updateUser(@Valid @RequestBody User user) {
        User newUser = userService.updateUser(user);
        log.debug("Обновлен пользователь");
        return newUser;
    }

    @DeleteMapping
    @Validated
    public void deleteUser(@Valid @RequestBody User user) {
        userService.deleteUser(user);
        log.debug("Удалён пользователь: {}", user);
    }

    @GetMapping
    public List<User> findUsers() {
        List<User> users = userService.findUsers();
        log.debug("Получен список пользователей");
        return users;
    }

    @GetMapping("/{userId}")
    public User findUserById(@PathVariable long userId) {
        User user = userService.findUserById(userId);
        log.debug("Получен пользователь");
        return user;
    }

}