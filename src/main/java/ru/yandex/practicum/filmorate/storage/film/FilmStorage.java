package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    List<Film> findAll();

    Film updateFilm(long id, Film film);

    Film create(Film film);

    boolean removeFilm(long id);

    List<Film> getTopFilms(int count);

}
