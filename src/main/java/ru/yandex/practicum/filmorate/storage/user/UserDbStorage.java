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
        validateUserId(user.getId());

        validateUser(user);

        String sql = "update USERS set EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? where ID = ?";

        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());

        String sqlCheckQuery = "select * from USERS where LOGIN = ?";
        return jdbcTemplate.queryForObject(sqlCheckQuery, this::resultSetToUser, user.getLogin());

    }

    private void validateUserId(int userId) {
        String sqlIdCheck = "select ID from USERS";
        List<Integer> ids = jdbcTemplate.query(sqlIdCheck, (rs, rowNum) -> (Integer.parseInt(rs.getString("ID"))));

        if (!ids.contains(userId)) {
            throw new NotFoundException("Пользователь с ID не найден в БД.");
        }
    }

    @Override
    public User createUser(User user) {
        validateUser(user);

        String sql = "insert into users(email, login, name, birthday) values(?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday().toString()
        );

        String sqlCheckQuery = "select * from USERS where LOGIN = ?";
        return jdbcTemplate.queryForObject(sqlCheckQuery, this::resultSetToUser, user.getLogin());

    }

    @Override
    public List<User> getAll() {
        String sql = "select * from USERS order by ID";

        return this.jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    User user = new User();
                    user.setId(Integer.parseInt(resultSet.getString("ID")));
                    user.setEmail(resultSet.getString("EMAIL"));
                    user.setLogin(resultSet.getString("LOGIN"));
                    user.setName(resultSet.getString("NAME"));
                    user.setBirthday(LocalDate.parse(resultSet.getString("BIRTHDAY")));
                    return user;
                });
    }

    @Override
    public boolean isUserExist(Integer id) {
        String sql = "SELECT COUNT(*) FROM USERS WHERE ID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
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
    public User getById(int userId) {
        String sql = "select * from USERS where id = ?";
        return jdbcTemplate.queryForObject(sql, this::resultSetToUser, userId);

    }

    private User resultSetToUser(ResultSet rs, int rowNum) throws SQLException {
        User resultUser = new User();

        resultUser.setId(rs.getInt("ID"));
        resultUser.setEmail(rs.getString("EMAIL"));
        resultUser.setLogin(rs.getString("LOGIN"));
        resultUser.setName(rs.getString("NAME"));
        resultUser.setBirthday(rs.getDate("BIRTHDAY").toLocalDate());

        return resultUser;
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
