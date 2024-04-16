package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validation.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmServiceImpl;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@Validated
public class FilmController {
    private final FilmStorage filmStorage;
    private final FilmServiceImpl filmServiceImpl;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmServiceImpl filmServiceImpl) {
        this.filmStorage = filmStorage;
        this.filmServiceImpl = filmServiceImpl;
    }
    @GetMapping("/{userId}")
    public Film getFilmById(@PathVariable int userId) {
        return filmStorage.getFilmById(userId);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmStorage.create(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        return filmStorage.updateFilm(film);
    }

    @GetMapping("/top")
    public List<Film> getTopRatedFilms(@RequestParam(defaultValue = "10") int count) {
        return filmServiceImpl.getTopRatedFilms(count);
    }

    @GetMapping
    public List<Film> getAllFilms() {
        if (!filmServiceImpl.getFilms().isEmpty()) {
            List<Film> filmList = new ArrayList<>(filmServiceImpl.getFilms().values());
            return filmList;
        } else {
            log.info("Список фильмов пуст");
            return new ArrayList<>();
        }
    }

    @DeleteMapping("/films/{filmId}/likes/{userId}")
    public void removeFilm(@PathVariable int filmId, @PathVariable int userId) {
        filmStorage.removeLike(filmId, userId);
    }

    @GetMapping("/{id}")
    public Film findById(@PathVariable long id) {
        return filmServiceImpl.findById(id);
    }


}

