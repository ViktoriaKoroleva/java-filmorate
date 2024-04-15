package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final UserService userService;
    private final UserStorage userStorage;

    public UserController(UserService userService, UserStorage userStorage) {
        this.userService = userService;
        this.userStorage = userStorage;
    }

    @PostMapping("/{userId1}/friends/{userId2}")
    public User addFriendship(@PathVariable int userId1, @PathVariable int userId2) {
        return userService.addFriendship(userId1, userId2);
    }

    @PostMapping
    public User createUser(@RequestBody @Valid User user) throws ValidationException {
        return userStorage.createUser(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriendsList(@PathVariable int id, @PathVariable int friendId) {
        return userService.addFriendship(id, friendId);
    }

    @DeleteMapping("/{userId1}/friends/{userId2}")
    public User removeFriendship(@PathVariable int userId1, @PathVariable int userId2) {
        return userService.removeFriend(userId1, userId2);
    }

    @GetMapping("/{userId1}/friends/mutual/{userId2}")
    public List<User> findMutualFriends(@PathVariable int userId1, @PathVariable int userId2) {
        return userService.findMutualFriends(userId1, userId2);
    }
}