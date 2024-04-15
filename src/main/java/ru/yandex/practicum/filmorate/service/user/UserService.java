package ru.yandex.practicum.filmorate.service.user;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addFriendship(int userId1, int userId2) {
        User user1 = userStorage.getUserById(userId1);
        User user2 = userStorage.getUserById(userId2);
        if (user1 != null && user2 != null) {
            user1.getFriends().add(userId2);
            user2.getFriends().add(userId1);
            return userStorage.getUser(userId1);
        } else {
            throw new ValidationException("Один из пользователей не найден");
        }
    }

    public User update(User user) {
        return userStorage.updateUser(user);
    }

    public User removeFriend(int userId1, int userId2) {
        User user1 = userStorage.getUserById(userId1);
        User user2 = userStorage.getUserById(userId2);
        if (user1 != null && user2 != null) {
            user1.getFriends().add(userId2);
            user2.getFriends().add(userId1);
            return userStorage.getUser(userId1);
        } else {
            throw new ValidationException("Один из пользователей не найден");
        }
    }

    public List<User> findMutualFriends(int userId1, int userId2) {
        User user1 = userStorage.getUserById(userId1);
        User user2 = userStorage.getUserById(userId2);
        if (user1 != null && user2 != null) {
            List<Integer> mutualFriends = new ArrayList<>(user1.getFriends());
            mutualFriends.retainAll(user2.getFriends());
        }
        return null;
    }
}
