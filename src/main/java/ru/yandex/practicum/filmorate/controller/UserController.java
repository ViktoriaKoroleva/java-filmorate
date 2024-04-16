package ru.yandex.practicum.filmorate.controller;

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
@Validated
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userId1}/friends/{userId2}")
    public User addFriendship(@PathVariable int userId1, @PathVariable int userId2) {
        return userService.addFriendship(userId1, userId2);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/{userId}/friends")
    public List<User> getUserFriends(@PathVariable int userId) {
        return userService.getUserFriends(userId);
    }

    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        return userService.createUser(user);
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