package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User addFriendship(int userId1, int friendId) {
        User user = getById(userId1);
        User friend = getById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId1);
        return user;
    }


    public User update(User user) {
        return userStorage.updateUser(user);
    }

    public User removeFriend(int userId1, int userId2) {
        return userStorage.removeFriend(userId1, userId2);
    }

    public List<User> getUserFriends(int userId) {
        return null;
    }

    public User getById(int userId) {
        return userStorage.getById(userId);
    }

    public void deleteById(Integer userId) {
        userStorage.deleteById(userId);
    }

    public List<User> getAllFriends(Integer userId) {
        return getById(userId).getFriends().stream()
                .map(this::getById)
                .collect(Collectors.toList());

    }
}
