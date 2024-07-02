package ru.yandex.practicum.filmorate.storage.friend;


import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendStorage {

    boolean addFriendship(User friendRequest, User friendResponse);

    boolean deleteFriendship(User friendRequest, User friendResponse);

    List<Long> getFriendsById(long id);

}
