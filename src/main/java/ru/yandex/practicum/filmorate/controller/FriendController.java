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
        return userService.addInFriends(id, friendId);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public boolean deleteFromFriends(@PathVariable long id, @PathVariable long friendId) {
        return userService.deleteFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> findFriends(@PathVariable long id) {
        return userService.findFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findMutualFriends(@PathVariable long id, @PathVariable long otherId) {
        List<User> users = userService.findMutualFriends(id, otherId);
        log.debug("Получен список друзей пользователя");
        return users;
    }
}
