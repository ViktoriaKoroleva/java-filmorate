package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User updateUser(User user) {

        String sqlQuery = "update users set login=?, " +
                "name = ?, " +
                "email=?, " +
                "birthday=? " +
                "where id= ?";
        jdbcTemplate.update(sqlQuery, user.getLogin(), user.getName(), user.getEmail(), user.getBirthday(), user.getId());
        return getById(user.getId());

    }

    @Override
    public User createUser(User user) {
        String sqlQuery = "insert into users (login, name, email, birthday) values (?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery, user.getLogin(), user.getName(), user.getEmail(), user.getBirthday());
        String sqlQueryUser = "select * from users where login= ? and name=? and email=? and birthday=?";
        User userFromBd = jdbcTemplate.queryForObject(sqlQueryUser, UserDbStorage::createUser, user.getLogin(), user.getName(),
                user.getEmail(), user.getBirthday());
        return userFromBd;

    }

    static User createUser(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .login(rs.getString("login"))
                .email(rs.getString("email"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
    }

    @Override
    public List<User> getAll() {
        String sqlQuery = "select * from users";
        return jdbcTemplate.query(sqlQuery, UserDbStorage::createUser);
    }

    @Override
    public List<Long> getIdUsers() {
        String sqlQuery = "select id from users ";
        List<Long> idUsers = jdbcTemplate.queryForList(sqlQuery, Long.class);
        return idUsers;
    }

    @Override
    public User getById(Long userId) {
        String sqlQuery = "select * from users where id= ?";
        User user = jdbcTemplate.queryForObject(sqlQuery, UserDbStorage::createUser, userId);
        return user;
    }
}
