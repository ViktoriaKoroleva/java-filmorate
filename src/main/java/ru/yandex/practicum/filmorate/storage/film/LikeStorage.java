package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikeStorage {
    void addLike(Long idFilm, Long idUser);

    void deleteLike(Long idFilm, Long idUser);

    List<Film> getPopularFilms(Long count);
}

