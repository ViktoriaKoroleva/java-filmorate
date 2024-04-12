package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.validation.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
@Validated
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private FilmService filmService;
    private int filmId = 1;

    private int generatorId() {
        return filmId++;
    }

    @GetMapping
    public List<Film> findAll() {
        log.info("Фильмов в коллекции: {}", films.size());
        return List.copyOf(films.values());
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        if (!isValidFilms(film)) {
            log.warn("Валидация не пройдена для фильма: {}", film);
            throw new ValidationException("Фильм не прошел валидацию");
        }
        film.setId(generatorId());
        films.put(film.getId(), film);
        log.info("Фильм успешно добавлен: {}", film);
        return film;
    }

    @PostMapping("/{filmId}/like")
    public void likeFilm(@PathVariable int filmId, @RequestParam int userId) {
        filmService.likeFilm(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like")
    public void removeLike(@PathVariable int filmId) {
        filmService.removeLike(filmId);
    }

    @GetMapping("/top")
    public List<Film> getTopFilms() {
        return filmService.getTopFilms();
    }

    private boolean isValidFilms(Film film) {
        return film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 27))
                && film.getReleaseDate().isBefore(LocalDate.now());
    }
}
