package ru.yandex.practicum.filmorate.storage.friend;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface FriendStorage {
    void addFriend(long userId, long friendId);

    void removeFriend(long userId, long friendId);

    Map<Long, User> getFriends(long userId);
}

