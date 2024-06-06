package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage {
    Film create(Film film);

    Film update(Film film);

    Map<Long, Film> getAll();

    Film getById(Long id);

    List<Long> getIdFilms();

    void deleteById(Integer id);
}
