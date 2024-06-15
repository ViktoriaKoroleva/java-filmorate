package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.ValidatorControllers;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;


import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    public User createUser(User user) {
        user = ValidatorControllers.validateUser(user);
        return userStorage.createUser(user).get();
    }

    public User update(User user) {
        user = ValidatorControllers.validateUser(user);
        if (getById(user.getId()) == null) {
            return null;
        }
        return userStorage.updateUser(user).get();
    }

    public List<User> getAll() {
        return new ArrayList<>(userStorage.getAll());
    }

    public User getById(Long id) {
        return userStorage.getById(id).get();
    }

    public boolean deleteUser(User user) {
        return userStorage.deleteUser(user);
    }

    public boolean addFriendship(long idUser, long idFriend) {
        if ((getById(idUser) == null) || (getById(idFriend) == null)) {
            return false;
        }
        User friendRequest = userStorage.getById(idUser).get();
        User friendResponse = userStorage.getById(idFriend).get();
        friendStorage.addFriend(friendRequest, friendResponse);
        return true;
    }

    public boolean removeFriend(Long userId, Long friendId) {
        if ((getById(userId) == null) || (getById(friendId) == null)) {
            return false;
        }
        User friendRequest = userStorage.getById(userId).get();
        User friendResponse = userStorage.getById(friendId).get();
        friendStorage.removeFriend(friendRequest, friendResponse);
        return true;
    }

    public List<User> getUserFriends(Long userId) {
        if (getById(userId) == null) {
            return Collections.EMPTY_LIST;
        }
        return friendStorage.findFriends(userId).stream()
                .map(this::getById)
                .collect(Collectors.toList());
    }

    public List<User> findCommonFriends(Long idUser, Long idOtherUser) {
        if ((getById(idUser) == null) || (getById(idOtherUser) == null)) {
            return Collections.EMPTY_LIST;
        }
        return getUserFriends(idUser).stream()
                .filter(f -> getUserFriends(idOtherUser).contains(f))
                .collect(Collectors.toList());
    }
}