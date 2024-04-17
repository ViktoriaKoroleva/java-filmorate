package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public User removeFriend(int userId1, int friendId) {
        getById(userId1).getFriends().remove(friendId);
        getById(friendId).getFriends().remove(userId1);
        return getById(userId1);
    }

    public List<User> getUserFriends(int id) {
        return getById(id).getFriends().stream()
                .map(this::getById)
                .collect(Collectors.toList());
    }

    public User getById(int userId) {
        return userStorage.getById(userId);
    }

    public void deleteById(Integer userId) {
        userStorage.deleteById(userId);
    }

    public Set<User> getAll() {
        return userStorage.getAll();
    }

    public List<User> getAllFriends(Integer userId) {
        return getById(userId).getFriends().stream()
                .map(this::getById)
                .collect(Collectors.toList());

    }

    public List<User> findCommonFriends(Integer userId, Integer friendId) {
        Set<Integer> mutualFriends = new HashSet<>(getById(userId).getFriends());
        mutualFriends.retainAll(getById(friendId).getFriends());
        return mutualFriends.stream().map(this::getById).collect(Collectors.toList());

    }
}
