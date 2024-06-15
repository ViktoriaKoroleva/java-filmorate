package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        return userService.createUser(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getById(userId);
    }

    @DeleteMapping
    @Validated
    public void deleteUser(@Valid @RequestBody User user) {
        userService.deleteUser(user);
        log.debug("Удалён пользователь: {}", user);
    }

    @GetMapping("/{userId}/friends/common/{friendId}")
    public List<User> findCommonFriends(@PathVariable Long userId, @PathVariable Long friendId) {
        return userService.findCommonFriends(userId, friendId);
    }

}