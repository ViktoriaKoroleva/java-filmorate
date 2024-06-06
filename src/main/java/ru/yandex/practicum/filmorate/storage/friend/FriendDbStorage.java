package ru.yandex.practicum.filmorate.storage.friend;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    private static Map<Long, User> mapUserMap(ResultSet rs) throws SQLException {
        Map<Long, User> usersMap = new HashMap<>();
        while (rs.next()) {
            usersMap.put(rs.getLong("id"), User.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .login(rs.getString("login"))
                    .email(rs.getString("email"))
                    .birthday(rs.getDate("birthday").toLocalDate())
                    .build());
        }
        return usersMap;
    }

    @Override
    public void addFriend(long userId, long friendId) {
        String query = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(query, userId, friendId);
    }

    @Override
    public void removeFriend(long userId, long friendId) {
        String query = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(query, userId, friendId);
    }

    @Override
    public Map<Long, User> getFriends(long userId) {
        String query = "SELECT u.id, u.login, u.name, u.email, u.birthday " +
                "FROM friends f " +
                "LEFT JOIN users u ON u.id = f.friend_id " +
                "WHERE f.user_id = ?";
        return jdbcTemplate.query(query, FriendDbStorage::mapUserMap, userId);
    }
}
