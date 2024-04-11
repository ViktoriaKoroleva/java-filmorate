package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final Map<Integer, User> userHashMap = new HashMap<>();
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

    @DeleteMapping("/{userId1}/friends/{userId2}")
    public void removeFriendship(@PathVariable Long userId1, @PathVariable Long userId2) {
        userService.removeFriend(userId1, userId2);
    }

    @GetMapping("/{userId1}/mutual-friends/{userId2}")
    public Set<Long> findMutualFriends(@PathVariable Long userId1, @PathVariable Long userId2) {
        return userService.findMutualFriends(userId1, userId2);
    }
}