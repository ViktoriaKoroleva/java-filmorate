package ru.yandex.practicum.filmorate.storage.friend;


import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendStorage {
    boolean addFriend(User friendRequest, User friendResponse);

    boolean removeFriend(User friendRequest, User friendResponse);

    List<Long> findFriends(long id);
}

