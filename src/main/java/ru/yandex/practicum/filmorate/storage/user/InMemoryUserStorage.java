package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;


import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return user;
        }
        return user;
    }

    @Override
    public User removeFriend(int userId1, int userId2) {
        getUserById(userId1).getFriends().remove(userId2);
        getUserById(userId2).getFriends().remove(userId1);
        return getUserById(userId1);
    }

    @Override
    public User getUserById(int id) {
        for (User user : users.values()) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> findMutualFriends(int userId, int userId2) {
        List<User> mutualFriends = new ArrayList<>();
        for (Integer id : getUserById(userId).getFriends()) {
            if (getUserById(userId2).getFriends().contains(id)) {
                mutualFriends.add(getUserById(id));
            }
        }
        return mutualFriends;
    }

    @Override
    public User createUser(User user) {
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return user;
        } else {
            throw new IllegalArgumentException("Пользователь с таким ID уже существует");
        }
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUser(int id) {
        return getUserById(id);
    }

}
