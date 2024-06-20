package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Optional<Film> createFilm(Film film);

    List<Film> findFilms();

    Optional<Film> findFilmById(long filmId);

    List<Genre> getAllGenres();

    Optional<Film> updateFilm(Film film);

    boolean deleteFilm(Film film);

    Optional<Genre> getGenreById(long genreId);

    List<Mpa> getAllRatingMPAs();

    Optional<Mpa> getRatingMPAById(long ratingMPAId);

}
