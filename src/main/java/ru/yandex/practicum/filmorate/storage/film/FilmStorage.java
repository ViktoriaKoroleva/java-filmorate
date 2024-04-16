package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;

public interface FilmStorage {

    Film getFilmById(long userId);

    Film updateFilm(Film film);

    Film create(Film film);

    Film removeLike(int filmId, int userId);

    List<Film> getTopFilms(int count);

    HashMap<Integer, Film> getFilms();

    List<Film> findAll();
}
