package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private int userId = 1;

    private final UserService userService;
    private final UserStorage userStorage;

    public UserController(UserService userService, UserStorage userStorage) {
        this.userService = userService;
        this.userStorage = userStorage;
    }

    private int generatorId() {
        return userId++;
    }

    @PostMapping("/{userId1}/friends/{userId2}")
    public void addFriendship(@PathVariable Long userId1, @PathVariable Long userId2) {
        userService.addFriendship(userId1, userId2);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{userId1}/friends/{userId2}")
    public void removeFriendship(@PathVariable Long userId1, @PathVariable Long userId2) {
        userService.removeFriend(userId1, userId2);
    }

    @GetMapping("/{userId1}/mutualfriends/{userId2}")
    public List<User> findMutualFriends(@PathVariable long userId1, @PathVariable long userId2) {
        return userService.findMutualFriends(userId1, userId2);
    }
}