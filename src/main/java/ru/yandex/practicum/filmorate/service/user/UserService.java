package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    User addFriendship(int userId1, int userId2);

    User update(User user);

    User removeFriend(int userId1, int userId2);

    List<User> findMutualFriends(int userId1, int userId2);

    List<User> getUserFriends(int userId);

    User getUserById(int userId);

    User createUser(User user);
}
