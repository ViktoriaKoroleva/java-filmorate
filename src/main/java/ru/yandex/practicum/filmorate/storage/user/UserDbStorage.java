package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.NotFoundException;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User updateUser(User user) {
        validateUser(user);

        String sql = "update users set login=?, " +
                "name = ?, " +
                "email=?, " +
                "birthday=? " +
                "where id= ?";

        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());

        return getById(user.getId());

    }

    @Override
    public User createUser(User user) {
        validateUser(user);

        String sql = "insert into users (login, name, email, birthday) values(?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday().toString()
        );

        String sqlCheckQuery = "select * from users where login= ? and name=? and email=? and birthday=?";
        User userFromBd = jdbcTemplate.queryForObject(sqlCheckQuery, UserDbStorage::createUser, user.getLogin(), user.getName(),
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
    public void deleteById(Integer userId) {
        String sql = "DELETE FROM USERS WHERE ID = ?";
        int rowsAffected = jdbcTemplate.update(sql, userId);
        if (rowsAffected == 0) {
            throw new NotFoundException("Пользователь с ID " + userId + " не найден в БД.");
        }
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

    private void validateUser(User user) throws ValidationException {

        if (user.getEmail() == null || !(user.getEmail().contains("@"))) {
            throw new ValidationException("Email не может быть пустым, должен содержать символ @");
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }

        if (user.getLogin() == null || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }

        if ((user.getName() == null)) {
            user.setName(user.getLogin());
        }
    }
}
