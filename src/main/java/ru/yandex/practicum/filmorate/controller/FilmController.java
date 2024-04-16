package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.validation.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@Validated
public class FilmController {
    private FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/{userId}")
    public Film getFilmById(@PathVariable int userId) {
        return filmService.getFilmById(userId);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        return filmService.updateFilm(film);
    }

    @GetMapping("popular")
    public List<Film> getTopRatedFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getTopRatedFilms(count);
    }

    @GetMapping
    public List<Film> findAll() {
        return filmService.findAll();
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void removeFilm(@PathVariable int filmId, @PathVariable int userId) {
        filmService.removeLike(filmId, userId);
    }

    @GetMapping("/{id}")
    public Film findById(@PathVariable long id) {
        return filmService.findById(id);
    }


}

