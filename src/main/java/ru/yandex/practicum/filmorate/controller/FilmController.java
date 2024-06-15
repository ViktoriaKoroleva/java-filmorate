package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping("/{filmId}")
    public Film getById(@PathVariable long filmId) {
        return filmService.findFilmById(filmId);
    }

    @GetMapping
    public List<Film> getAll() {
        return filmService.findFilms();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmService.createFilm(film);
    }

    @DeleteMapping
    public void delete(@Valid @RequestBody Film film) {
        filmService.deleteFilm(film);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable long filmId, @PathVariable long userId) {
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteLike(@PathVariable long filmId, @PathVariable long userId) {
        filmService.deleteLike(filmId, userId);
    }

    @GetMapping
    public List<Film> findFilms() {
        return filmService.findFilms();
    }

    @GetMapping("/{filmId}")
    public Film findFilmById(@PathVariable long filmId) {
        return filmService.findFilmById(filmId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") String count) {
        return filmService.getPopularFilms(count);
    }
}