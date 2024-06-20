package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
@RequiredArgsConstructor
public class FriendController {

    private final UserService userService;

    @PutMapping("/{id}/friends/{friendId}")
    public boolean addInFriends(@PathVariable long id, @PathVariable long friendId) {
        return userService.addFriendship(id, friendId);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public boolean deleteFromFriends(@PathVariable long id, @PathVariable long friendId) {
        return userService.deleteFriendship(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriendsById(@PathVariable long id) {
        return userService.getFriendsById(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriendsByIds(@PathVariable long id, @PathVariable long otherId) {
        return userService.getMutualFriendsByIds(id, otherId);
    }
}
