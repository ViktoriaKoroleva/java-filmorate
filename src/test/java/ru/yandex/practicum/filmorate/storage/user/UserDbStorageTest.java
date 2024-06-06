package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

@JdbcTest // указываем, о необходимости подготовить бины для работы с БД
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    UserDbStorage userStorage;

    @BeforeEach
    public void set() {
        userStorage = new UserDbStorage(jdbcTemplate);
    }

    @Test
    public void testFindUserById() {
        User user = new User("user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        User newUser = userStorage.createUser(user);
        user.setId(newUser.getId());
        User savedUser = userStorage.getById(newUser.getId());

        Assertions.assertThat(savedUser)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(newUser);        // и сохраненного пользователя - совпадают
    }

    @Test
    public void testFindAllUsers() {
        User newUser = new User("user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        userStorage.createUser(newUser);

        User newUser2 = new User("user2@email.ru", "User2", "User2", LocalDate.of(1993, 3, 1));
        userStorage.createUser(newUser2);
        newUser.setId(1L);
        newUser2.setId(2L);

        List<User> savedUsers = userStorage.getAll();

        Assertions.assertThat(savedUsers)
                .isNotNull()
                .size().isEqualTo(2);

        Assertions.assertThat(savedUsers.get(0))
                .usingRecursiveComparison()
                .isEqualTo(newUser);

        Assertions.assertThat(savedUsers.get(1))
                .usingRecursiveComparison()
                .isEqualTo(newUser2);
    }

    @Test
    public void testUpdateUser() {
        User user = new User("user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        userStorage.createUser(user);

        List<User> users = userStorage.getAll();
        User user1 = users.get(0);
        user1.setName("New name");
        user1.setLogin("New login");

        userStorage.updateUser(user1);

        User updatedUser = userStorage.getById(user1.getId());

        Assertions.assertThat(user1).usingRecursiveComparison().isEqualTo(updatedUser);
    }
}
