package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;


public interface UserStorage {

    User updateUser(User user);

    User removeFriend(int userId1, int userId2);

    User createUser(User user);

    boolean isUserExist(Integer id);

    void deleteById(Integer userId);

    User getById(int userId);
}
