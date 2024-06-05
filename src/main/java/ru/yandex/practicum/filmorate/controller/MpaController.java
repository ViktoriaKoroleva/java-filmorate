package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.mpa.MpaService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
@AllArgsConstructor
public class MpaController {
    private final MpaService service;

    @GetMapping
    public List<Mpa> getAll() {
        final List<Mpa> mpa = service.getAll();
        log.info("Get all mpa {}", mpa);
        return mpa;
    }

    @GetMapping("/{id}")
    public Mpa get(@PathVariable long id) {
        return service.get(id);
    }
}
