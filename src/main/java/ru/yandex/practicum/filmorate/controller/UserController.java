package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserServiceImpl;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final UserStorage userStorage;

    public UserController(UserServiceImpl userServiceImpl, UserStorage userStorage) {
        this.userServiceImpl = userServiceImpl;
        this.userStorage = userStorage;
    }

    @PostMapping("/{userId1}/friends/{userId2}")
    public User addFriendship(@PathVariable int userId1, @PathVariable int userId2) {
        return userServiceImpl.addFriendship(userId1, userId2);
    }
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userStorage.getUserById(userId);
    }
    @GetMapping("/{userId}/friends")
    public List<User> getUserFriends(@PathVariable int userId) {
        return userServiceImpl.getUserFriends(userId);
    }
    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        return userStorage.createUser(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userServiceImpl.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriendsList(@PathVariable int id, @PathVariable int friendId) {
        return userServiceImpl.addFriendship(id, friendId);
    }

    @DeleteMapping("/{userId1}/friends/{userId2}")
    public User removeFriendship(@PathVariable int userId1, @PathVariable int userId2) {
        return userServiceImpl.removeFriend(userId1, userId2);
    }

    @GetMapping("/{userId1}/friends/mutual/{userId2}")
    public List<User> findMutualFriends(@PathVariable int userId1, @PathVariable int userId2) {
        return userServiceImpl.findMutualFriends(userId1, userId2);
    }
}