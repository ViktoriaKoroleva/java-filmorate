package ru.yandex.practicum.filmorate.controller;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.time.LocalDate;

@NoArgsConstructor
@Slf4j
public class ValidatorControllers {

    public static Film validateFilm(Film film) {
        ValidatorControllers.validateName(film.getName());
        ValidatorControllers.validateReleaseDate(film.getReleaseDate());
        return film;
    }

    private static void validateName(String name) {
        if (name.isBlank()) {
            logAndError("Ошибка! Название не может быть пустым.");
        }
    }

    private static void validateReleaseDate(LocalDate releaseDate) {
        if (releaseDate.isBefore(LocalDate.of(1895, 12, 28))) {
            logAndError("Ошибка! Дата релиза — не раньше 28 декабря 1895 года.");
        }
    }

    public static User validateUser(User user) {
        ValidatorControllers.validateLogin(user.getLogin());
        user = validateUserName(user);
        ValidatorControllers.validateBirthday(user.getBirthday());
        return user;
    }

    private static User validateUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }

    private static void validateLogin(String login) {
        if (login.contains(" ")) {
            logAndError("Ошибка! Логин не может быть пустым и содержать пробелы.");
        }
    }

    private static void validateBirthday(LocalDate birthday) {
        if (birthday.isAfter(LocalDate.now())) {
            logAndError("Ошибка! Дата рождения не может быть в будущем.");
        }
    }

    private static void logAndError(String exp) {
        log.warn(exp);
        throw new ValidationException(exp);
    }

}
