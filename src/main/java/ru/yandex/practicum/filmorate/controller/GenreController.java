package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import java.util.List;

@RestController
@RequestMapping("/genres")
@Slf4j
@Validated
@RequiredArgsConstructor
public class GenreController {

    private final FilmService filmService;

    @GetMapping
    public List<Genre> findGenres() {
        return filmService.findGenres();
    }

    @GetMapping("/{id}")
    public Genre findGenreById(@PathVariable long id) {
        return filmService.findGenreById(id);
    }

}

