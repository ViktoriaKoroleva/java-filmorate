package ru.yandex.practicum.filmorate.service.user;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User createUser(User user){
        return userStorage.createUser(user);
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

    @Override
    public User update(User user) {
        return userStorage.updateUser(user);
    }

    @Override
    public User removeFriend(int userId1, int userId2) {
        return userStorage.removeFriend(userId1, userId2);
    }

    @Override
    public List<User> findMutualFriends(int userId1, int userId2) {
        return userStorage.findMutualFriends(userId1, userId2);
    }

    @Override
    public List<User> getUserFriends(int userId) {
        return null;
    }

    @Override
    public User getUserById(int userId) {
        return null;
    }
}
