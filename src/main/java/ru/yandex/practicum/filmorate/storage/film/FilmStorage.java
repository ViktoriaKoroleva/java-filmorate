package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Optional<Film> createFilm(Film film);

    Optional<Film> updateFilm(Film film);

    List<Film> getAll();

    List<Mpa> ratingMPASearch();

    boolean deleteFilm(Film film);

    Optional<Mpa> ratingMPASearchById(long ratIdMpa);

    Optional<Film> getFilmById(long filmId);

    List<Genre> findGenres();

    Optional<Genre> findGenreById(long genreId);


}
