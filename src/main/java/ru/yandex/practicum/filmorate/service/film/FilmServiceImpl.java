package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;

@Service
@Slf4j
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;

    @Override
    public Film create(Film film) {
        filmStorage.create(film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        filmStorage.updateFilm(film);
        return film;
    }

    @Override
    public Film getFilmById(long userId) {
        return filmStorage.getFilmById(userId);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        filmStorage.removeLike(filmId, userId);
    }

    @Autowired
    public FilmServiceImpl(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }


    public HashMap<Integer, Film> getFilms() {
        return filmStorage.getFilms();
    }

    @Override
    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film findById(long filmId) {
        return filmStorage.getFilmById(filmId);
    }

    @Override
    public List<Film> getTopRatedFilms(int count) {
        return filmStorage.getTopRatedFilms(count);
    }

}
