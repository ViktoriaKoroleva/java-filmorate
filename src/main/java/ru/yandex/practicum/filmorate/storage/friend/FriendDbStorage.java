package ru.yandex.practicum.filmorate.storage.friend;

import org.springframework.jdbc.core.JdbcTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean addFriend(User friendRequest, User friendResponse) {
        String sqlQuery = "merge into friends(request_friend_id,response_friend_id) values (?, ?)";
        return jdbcTemplate.update(sqlQuery, friendRequest.getId(), friendResponse.getId()) > 0;

    }

    public boolean removeFriend(User friendRequest, User friendResponse) {
        String query = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        return jdbcTemplate.update(query, friendRequest.getId(), friendResponse.getId()) > 0;

    }

    public List<Long> findFriends(long id) {
        String sqlQuery = "select response_friend_id from friends " +
                "where request_friend_id = ?";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> rs.getLong("response_friend_id"), id);

    }
}
