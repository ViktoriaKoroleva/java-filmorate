package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User addUser(User user);

    User getUserById(long id);

    User updateUser(long id, User user);

    boolean deleteUser(long id);

    User removeFriend(long userId, long friendId);

    List<User> getFriendsByUserId(long id);

    List<User> findAllUsers();

    List<User> findMutualFriends(long userId, long friendId);
}
