package ru.yandex.practicum.filmorate.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.ValidationException;

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
    public Film create(@Valid @RequestBody Film film) throws ValidationException {

        validateFilms(film);
        film.setId(generatorId());
        films.put(film.getId(), film);
        log.info("Фильм успешно добавлен: {}", film);

        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        validateFilms(film);
        if (!films.containsKey(film.getId())) {

            log.warn("Невозможно обновить фильм");
            throw new ValidationException("Невозможно обновить фильм");
        }
        films.put(film.getId(), film);
        log.info("Фильм c id" + film.getId() + " обновлён");
        return film;
    }

    public void validateFilms(Film film)  {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Валидация не пройдена. Причина - дата релиза фильма");
            throw new ValidationException("Дата релиза не может быть раньше чем 28.12.1895");
        }
    }
}