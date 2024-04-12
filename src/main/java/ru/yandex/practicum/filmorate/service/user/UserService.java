package ru.yandex.practicum.filmorate.service.user;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final Set<User> users = new HashSet<>();
    private UserStorage userStorage;

    public User addUser(User user) {
        users.add(user);
        return user;
    }

    public void addFriendship(Long userId1, Long userId2) {
        User user1 = findUserById(userId1);
        User user2 = findUserById(userId2);
        if (user1 != null && user2 != null) {
            user1.getFriends().add(userId2);
            user2.getFriends().add(userId1);
        }
    }

    public User update(User user) {
        return userStorage.updateUser(user);
    }

    public User removeFriend(long userId, long friendId) {

        return userStorage.removeFriend(userId, friendId);
    }

    public List<User> getFriendsById(long userId) {

        return userStorage.getFriendsById(userId);
    }

    public Set<Long> findMutualFriends(Long userId1, Long userId2) {
        User user1 = findUserById(userId1);
        User user2 = findUserById(userId2);
        if (user1 != null && user2 != null) {
            Set<Long> mutualFriends = new HashSet<>(user1.getFriends());
            mutualFriends.retainAll(user2.getFriends());
            return mutualFriends;
        }
        return new HashSet<>();
    }

    public List<User> findMutualFriends(long userId, long otherUserId) {
        return userStorage.findMutualFriends(userId, otherUserId);
    }

    private User findUserById(Long userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}
