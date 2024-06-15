package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.ValidatorControllers;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;


import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private final FriendStorage friendStorage;


    public User create(User user) {
        user = ValidatorControllers.validateUser(user);
        return userStorage.create(user).get();
    }

    public User update(User user) {
        user = ValidatorControllers.validateUser(user);
        if (findUserById(user.getId()) == null) {
            return null;
        }
        return userStorage.update(user).get();
    }

    public boolean delete(User user) {
        return userStorage.delete(user);
    }

    public List<User> findUsers() {
        return userStorage.findUsers();
    }

    public User findUserById(long userId) {
        return userStorage.findUserById(userId).get();
    }

    public boolean addInFriends(long id, long friendId) {
        if ((findUserById(id) == null) || (findUserById(friendId) == null)) {
            return false;
        }
        User friendRequest = userStorage.findUserById(id).get();
        User friendResponse = userStorage.findUserById(friendId).get();
        friendStorage.addInFriends(friendRequest, friendResponse);
        return true;
    }

    public boolean deleteFromFriends(long id, long friendId) {
        if ((findUserById(id) == null) || (findUserById(friendId) == null)) {
            return false;
        }
        User friendRequest = userStorage.findUserById(id).get();
        User friendResponse = userStorage.findUserById(friendId).get();
        friendStorage.deleteFromFriends(friendRequest, friendResponse);
        return true;
    }

    public List<User> findFriends(long id) {
        if (findUserById(id) == null) {
            return Collections.EMPTY_LIST;
        }
        return friendStorage.findFriends(id).stream()
                .map(this::findUserById)
                .collect(Collectors.toList());
    }

    public List<User> findMutualFriends(long id, long otherId) {
        if ((findUserById(id) == null) || (findUserById(otherId) == null)) {
            return Collections.EMPTY_LIST;
        }
        return findFriends(id).stream()
                .filter(f -> findFriends(otherId).contains(f))
                .collect(Collectors.toList());
    }

}