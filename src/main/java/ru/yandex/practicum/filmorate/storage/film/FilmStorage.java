package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film findById(long userId);

    Film updateFilm(Film film);

    Film create(Film film);

    Film removeLike(long filmId, long userId);

    List<Film> getTopFilms(int count);

}
