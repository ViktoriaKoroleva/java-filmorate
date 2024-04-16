package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;

public interface FilmService {
    Film findById(long filmId);

    Film create(Film film);

    Film getFilmById(long userId);

    Film updateFilm(Film film);

    HashMap<Integer, Film> getFilms();

    List<Film> findAll();

    List<Film> getTopRatedFilms(int count);

    void removeLike(int filmId, int userId);
}
