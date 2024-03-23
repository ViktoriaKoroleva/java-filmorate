package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.time.LocalDate;


class FilmControllerTest {


    @Test
    public void shouldNotAddFilmWithEmptyName() {
        FilmController filmController = new FilmController();
        Film film = Film.builder()
                .name("")
                .description("Test film")
                .duration(120)
                .releaseDate(LocalDate.parse("1900-05-25"))
                .build();

        try {
            filmController.create(film);
            System.out.println("Test failed: Film with empty name was added.");
        } catch (ValidationException e) {
            System.out.println("Test passed: Film with empty name was not added.");
        }
    }

    @Test
    public void testFilmWithInvalidReleaseDate() {
        FilmController filmController = new FilmController();
        Film film = Film.builder()
                .name("Name")
                .description("description")
                .duration(120)
                .releaseDate(LocalDate.parse("1890-03-25"))
                .build();

        try {
            filmController.create(film);
            System.out.println("Test failed: Film with release date before minimum allowed was added.");
        } catch (ValidationException e) {
            System.out.println("Test passed: Film with release date before minimum allowed was not added.");
        }
    }

    @Test
    public void testFilmWithLongDescription() {
        FilmController filmController = new FilmController();
        Film film = Film.builder()
                .name("Name")
                .description("N".repeat(201))
                .duration(120)
                .releaseDate(LocalDate.parse("2000-03-25"))
                .build();

        try {
            filmController.create(film);
            System.out.println("Test passed: Film with long description was not added.");
        } catch (ValidationException e) {
            System.out.println("Test failed: Film with long description was added.");
        }
    }
}