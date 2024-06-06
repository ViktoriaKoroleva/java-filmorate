package ru.yandex.practicum.filmorate.dao.film;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    FilmDbStorage filmStorage;
    MpaDbStorage mpaStorage;
    GenreDbStorage genreStorage;

    @BeforeEach
    public void set() {
        filmStorage = new FilmDbStorage(jdbcTemplate);
        mpaStorage = new MpaDbStorage(jdbcTemplate);
        genreStorage = new GenreDbStorage(jdbcTemplate);
    }


    @Test
    public void testFindFilmById() {
        Film film = new Film("Film 1", "Description 1", LocalDate.of(1990, 1, 1),
                150, mpaStorage.get(1L), List.of(genreStorage.get(1L)));
        Film newFilm = filmStorage.create(film);
        film.setId(newFilm.getId());

        Film savedFilm = filmStorage.getById(newFilm.getId());

        Assertions.assertThat(savedFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newFilm);
    }

    @Test
    public void testFindAllFilms() {
        Film film1 = new Film("Film 1", "Description 1", LocalDate.of(1990, 1, 1),
                150, mpaStorage.get(1L), List.of(genreStorage.get(1L)));
        Film newFilm1 = filmStorage.create(film1);
        film1.setId(newFilm1.getId());

        Film film2 = new Film("Film 2", "Description 2", LocalDate.of(2000, 12, 1),
                100, mpaStorage.get(2L), List.of(genreStorage.get(2L)));
        Film newFilm2 = filmStorage.create(film2);
        film2.setId(newFilm2.getId());

        List<Film> savedUsers = new ArrayList<>(filmStorage.getAll().values());

        Assertions.assertThat(savedUsers)
                .isNotNull()
                .size().isEqualTo(2);

        Assertions.assertThat(savedUsers.get(0))
                .usingRecursiveComparison()
                .isEqualTo(newFilm1);

        Assertions.assertThat(savedUsers.get(1))
                .usingRecursiveComparison()
                .isEqualTo(newFilm2);
    }

    @Test
    public void testUpdateUser() {
        Film film = new Film("Film 1", "Description 1", LocalDate.of(1990, 1, 1),
                150, mpaStorage.get(1L), List.of(genreStorage.get(1L)));
        filmStorage.create(film);

        List<Film> films = new ArrayList<>(filmStorage.getAll().values());
        Film film1 = films.get(0);
        film1.setName("New name");
        film1.setDescription("New description");

        filmStorage.update(film1);

        Film updatedFilm = filmStorage.getById(film1.getId());

        Assertions.assertThat(film1).usingRecursiveComparison().isEqualTo(updatedFilm);
    }

}