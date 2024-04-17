package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;


    public Film create(Film film) {
        filmStorage.create(film);
        return film;
    }

    public Film addLike(Integer filmId, Integer userId) {
        if (!userStorage.isUserExist(userId)) {
            throw new ValidationException("Нет пользователя с таким id.");
        }
        Film film = filmStorage.getById(filmId);
        film.getLike().add(userId);
        log.info("Добавлен лайк фильму: {} от пользователя c id: {}.", film.getName(), userId);
        return film;
    }

    public Film deleteLike(Integer filmId, Integer userId) {
        if (!userStorage.isUserExist(userId)) {
            throw new ValidationException("Нет пользователя с таким id.");
        }
        Film film = filmStorage.getById(filmId);
        film.getLike().remove(userId);
        log.info("Удален лайк фильму: {} от пользователя c id: {}.", film.getName(), userId);
        return film;
    }

    public Set<Film> getFilms() {
        return filmStorage.getAll();
    }

    public void deleteById(Integer filmId) {
        filmStorage.deleteById(filmId);
    }

    public Film getById(Integer filmId) {
        return filmStorage.getById(filmId);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public List<Film> fetchTopRatedFilms(int filmsCount) {
        int popularCount = (filmsCount > 0) ? filmsCount : 10;
        return filmStorage.getAll().stream()
                .sorted(Comparator.comparingInt((Film f) -> f.getLike().size()).reversed())
                .limit(popularCount)
                .collect(Collectors.toList());

    }
}
