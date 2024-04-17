package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.util.*;

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

    public Film updateFilm(Film film) {
        filmStorage.updateFilm(film);
        return film;
    }

    public void removeLike(int filmId, int userId) {
        filmStorage.removeLike(filmId, userId);
    }

    public HashMap<Integer, Film> getFilms() {
        return filmStorage.getFilms();
    }


    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film findById(long filmId) {
        return filmStorage.getFilmById(filmId);
    }


    public List<Film> getTopRatedFilms(int count) {
        return filmStorage.getTopRatedFilms(count);
    }

    public Film addLike(Integer filmId, Integer userId) {
        if (!userStorage.isUserExist(userId)) {
            throw new ValidationException("Нет пользователя с таким id.");
        }
        Film film = filmStorage.getFilmById(filmId);
        film.getLike().add(userId);
        log.info("Добавлен лайк фильму: {} от пользователя c id: {}.", film.getName(), userId);
        return film;
    }

    public Film deleteLike(Integer filmId, Integer userId) {
        if (!userStorage.isUserExist(userId)) {
            throw new ValidationException("Нет пользователя с таким id.");
        }
        Film film = filmStorage.getFilmById(filmId);
        film.getLike().remove(userId);
        log.info("Удален лайк фильму: {} от пользователя c id: {}.", film.getName(), userId);
        return film;
    }
}
