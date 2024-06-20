package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.ValidationControllers;
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


    public User createUser(User user) {
        user = ValidationControllers.validateUser(user);
        return userStorage.createUser(user).get();
    }

    public User updateUser(User user) {
        user = ValidationControllers.validateUser(user);
        if (findUserById(user.getId()) == null) {
            return null;
        }
        return userStorage.updateUser(user).get();
    }

    public boolean deleteUser(User user) {
        return userStorage.deleteUser(user);
    }

    public List<User> findUsers() {
        return userStorage.findUsers();
    }

    public User findUserById(long userId) {
        return userStorage.findUserById(userId).get();
    }

    public boolean addFriendship(long id, long friendId) {
        if ((findUserById(id) == null) || (findUserById(friendId) == null)) {
            return false;
        }
        User friendRequest = userStorage.findUserById(id).get();
        User friendResponse = userStorage.findUserById(friendId).get();
        friendStorage.addFriendship(friendRequest, friendResponse);
        return true;
    }

    public boolean deleteFriendship(long id, long friendId) {
        if ((findUserById(id) == null) || (findUserById(friendId) == null)) {
            return false;
        }
        User friendRequest = userStorage.findUserById(id).get();
        User friendResponse = userStorage.findUserById(friendId).get();
        friendStorage.deleteFriendship(friendRequest, friendResponse);
        return true;
    }

    public List<User> getFriendsById(long id) {
        if (findUserById(id) == null) {
            return Collections.EMPTY_LIST;
        }
        return friendStorage.getFriendsById(id).stream()
                .map(this::findUserById)
                .collect(Collectors.toList());
    }

    public List<User> getMutualFriendsByIds(long id, long otherId) {
        if ((findUserById(id) == null) || (findUserById(otherId) == null)) {
            return Collections.EMPTY_LIST;
        }
        return getFriendsById(id).stream()
                .filter(f -> getFriendsById(otherId).contains(f))
                .collect(Collectors.toList());
    }

}