package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
@Validated
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @DeleteMapping
    @Validated
    public void delete(@Valid @RequestBody Film film) {
        filmService.delete(film);
        log.debug("Удалён фильм: {}", film);
    }

    @PostMapping
    @Validated
    public Film create(@Valid @RequestBody Film film) {
        Film newFilm = filmService.create(film);
        log.debug("Добавлен новый фильм: {}", newFilm);
        return newFilm;
    }

    @PutMapping
    @Validated
    public Film update(@Valid @RequestBody Film film) {
        Film newFilm = filmService.update(film);
        log.debug("Обновлен фильм: {}", newFilm);
        return newFilm;
    }

    @GetMapping
    public List<Film> findFilms() {
        List<Film> films = filmService.findFilms();
        log.debug("Получен список фильмов, количество = {}", films.size());
        return films;
    }

    @GetMapping("/{filmId}")
    public Film findFilmById(@PathVariable long filmId) {
        Film film = filmService.findFilmById(filmId);
        log.debug("Получен фильм с id = {}", filmId);
        return film;
    }

    @PutMapping("/{id}/like/{userId}")
    public boolean like(@PathVariable long id, @PathVariable long userId) {
        log.debug("Пользователь id = {} желает лайкнуть фильм id = {}", userId, id);
        return filmService.like(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public boolean dislike(@PathVariable long id, @PathVariable long userId) {
        log.debug("Пользователь id = {} желает удалить лайк с фильма id = {}", userId, id);
        return filmService.dislike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> findPopularFilms(@RequestParam(defaultValue = "10") String count) {
        return filmService.findPopularFilms(count);
    }

}