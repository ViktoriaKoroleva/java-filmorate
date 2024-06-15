package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.ValidatorControllers;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.Like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikeStorage likeStorage;

    public Film createFilm(Film film) {
        film = ValidatorControllers.validateFilm(film);
        return filmStorage.createFilm(film).get();
    }

    public Film updateFilm(Film film) {
        ValidatorControllers.validateFilm(film);
        return filmStorage.updateFilm(film).get();
    }
    public boolean deleteFilm(Film film) {
        return filmStorage.deleteFilm(film);
    }
    public List<Film> findFilms() {
        return filmStorage.findFilms().stream()
                .peek(film -> film.setLikes(new HashSet<>(likeStorage.findLikes(film))))
                .collect(Collectors.toList());
    }

    public Film findFilmById(long filmId) {
        return filmStorage.getFilmById(filmId).stream()
                .peek(f -> f.setLikes(new HashSet<>(likeStorage.findLikes(f))))
                .findFirst().get();
    }

    public Genre findGenreById(long genreId) {
        return filmStorage.findGenreById(genreId)
                .orElseThrow(() -> {
                    log.warn("Жанр № {} не найден", genreId);
                    throw new ValidationException(String.format("Жанр № %d не найден", genreId));
                });
    }

    public List<Mpa> ratingMPASearch() {
        return filmStorage.ratingMPASearch();
    }

    public Mpa ratingMPASearchById(long ratIdMpa) {
        return filmStorage.ratingMPASearchById(ratIdMpa)
                .orElseThrow(() -> {
                    log.warn("Рейтинг МПА № {} не найден", ratIdMpa);
                    throw new ValidationException(String.format("Рейтинг МПА № %d не найден", ratIdMpa));
                });
    }

    public boolean deleteLike(long id, long userId) {
        if (findFilmById(id) == null || userStorage.getById(userId).isEmpty()) {
            return false;
        }

        Film film = findFilmById(id);
        film.getLikes().remove(userId);
        likeStorage.dislike(film);
        likeStorage.like(film);
        return true;
    }

    public List<Genre> findGenres() {
        return filmStorage.findGenres();
    }

    public boolean addLike(long id, long userId) {
        if (findFilmById(id) == null || userStorage.getById(userId).isEmpty()) {
            return false;
        }

        Film film = findFilmById(id);
        film.getLikes().add(userId);
        likeStorage.dislike(film);
        likeStorage.like(film);
        return true;
    }

    public List<Film> getPopularFilms(String count) {
        int countInt = Integer.parseInt(count);
        if (countInt < 0) {
            String message = "Параметр count не может быть отрицательным!";
            log.warn(message);
            throw new ValidationException(message);
        }

        return findFilms().stream()
                .sorted(this::compare)
                .limit(countInt)
                .collect(Collectors.toList());
    }

    private int compare(Film film1, Film film2) {
        return film2.getLikes().size() - film1.getLikes().size();
    }

}