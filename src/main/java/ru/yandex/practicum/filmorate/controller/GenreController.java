package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.genre.GenreService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
@AllArgsConstructor
public class GenreController {

    private final GenreService service;

    @GetMapping
    public List<Genre> getAll() {
        final List<Genre> genres = service.getAll();
        log.info("Get all genres {}", genres);
        return genres;
    }

    @GetMapping("/{id}")
    public Genre get(@PathVariable long id) {
        return service.get(id);
    }
}

