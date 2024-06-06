package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.ValidationException;


import java.util.*;

@Service
public class UserService {

    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    @Autowired
    private UserService(@Qualifier("userDbStorage") UserStorage userStorage, FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User update(User user) {
        List<Long> idsUsersFromBd = userStorage.getIdUsers();
        if (!(idsUsersFromBd.contains(user.getId()))) {
            throw new ValidationException("В БД нет пользователя с таким id");
        }
        return userStorage.updateUser(user);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User getById(Long id) {
        return userStorage.getById(id);
    }

    public void deleteById(Integer id) {
        userStorage.deleteById(id);
    }

    public void addFriendship(Long idUser, Long idFriend) {
        List<Long> idsUsersFromBd = userStorage.getIdUsers();
        if (!(idsUsersFromBd.contains(idUser))) {
            throw new ValidationException("В бд нет пользователя с таким id=" + idUser);
        }
        if (!(idsUsersFromBd.contains(idFriend))) {
            throw new ValidationException("В бд нет пользователя с таким id=" + idFriend);
        }
        friendStorage.addFriend(idUser, idFriend);

    }

    public void removeFriend(Long userId, Long friendId) {
        List<Long> idsUsersFromBd = userStorage.getIdUsers();
        if (!(idsUsersFromBd.contains(userId))) {
            throw new ValidationException("В бд нет пользователя с таким id=" + userId);
        }
        if (!(idsUsersFromBd.contains(userId))) {
            throw new ValidationException("В бд нет пользователя с таким id=" + userId);
        }
        friendStorage.removeFriend(userId, friendId);
    }

    public List<User> getUserFriends(Long userId) {
        List<Long> idsUsersFromBd = userStorage.getIdUsers();
        if (!(idsUsersFromBd.contains(userId))) {
            throw new ValidationException("В бд нет пользователя с таким id=" + userId);
        }
        return new ArrayList<>(friendStorage.getFriends(userId).values());
    }

    public List<User> findCommonFriends(Long idUser, Long idOtherUser) {
        userStorage.getById(idUser);
        userStorage.getById(idOtherUser);
        Map<Long, User> userFriends = friendStorage.getFriends(idUser);
        Map<Long, User> otherUserFriends = friendStorage.getFriends(idOtherUser);
        List<User> commonFriends = new ArrayList<>();

        for (Long idFriend : userFriends.keySet()) {
            if (otherUserFriends.containsKey(idFriend)) {
                commonFriends.add(userFriends.get(idFriend));
            }
        }

        return commonFriends;
    }
}