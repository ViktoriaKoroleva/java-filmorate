package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User addUser(User user);

    User getUserById(long id);

    User updateUser(User user);

    User removeFriend(long userId, long friendId);

    List<User> getFriendsById(long id);


    List<User> findMutualFriends(long userId, long friendId);
}
