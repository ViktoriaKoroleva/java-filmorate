package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@Slf4j
@Validated
@RequiredArgsConstructor
public class MpaController {
    private final FilmService filmService;

    @GetMapping
    public List<Mpa> findRatingMPAs() {
        List<Mpa> ratingMpas = filmService.findRatingMPAs();
        log.debug("Получен список рейтингов");
        return ratingMpas;
    }

    @GetMapping("/{id}")
    public Mpa findRatingMPAById(@PathVariable long id) {
        Mpa ratingMpa = filmService.findRatingMPAById(id);
        log.debug("Получен рейтинг МПА");
        return ratingMpa;
    }
}


