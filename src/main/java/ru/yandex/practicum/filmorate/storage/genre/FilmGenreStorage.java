package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmGenreStorage {
    List<Long> getIdsGenreByFilm(Long id);

    Map<Long, List<Long>> getAll();

    void createFilmGenre(Film film);
}
