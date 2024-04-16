package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User getUserById(int id);

    User updateUser(User user);

    User removeFriend(int userId1, int userId2);

    List<User> findMutualFriends(int userId1, int userId2);

    User createUser(User user);

    User getUser(int id);
}
