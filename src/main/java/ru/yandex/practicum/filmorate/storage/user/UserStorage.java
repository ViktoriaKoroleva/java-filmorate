package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    Optional<User> updateUser(User user);

    Optional<User> createUser(User user);

    List<User> getAll();

    boolean deleteUser(User user);

    Optional<User> getById(long userId);

}
