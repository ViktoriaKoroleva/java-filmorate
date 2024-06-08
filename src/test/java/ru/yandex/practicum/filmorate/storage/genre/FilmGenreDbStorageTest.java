package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;

import java.time.LocalDate;
import java.util.List;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmGenreDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    FilmDbStorage filmStorage;
    FilmGenreDbStorage filmGenreStorage;
    MpaDbStorage mpaStorage;
    GenreDbStorage genreStorage;


    @BeforeEach
    public void set() {
        filmStorage = new FilmDbStorage(jdbcTemplate);
        filmGenreStorage = new FilmGenreDbStorage(jdbcTemplate);
        mpaStorage = new MpaDbStorage(jdbcTemplate);
        genreStorage = new GenreDbStorage(jdbcTemplate);
    }

    @Test
    public void testFindGenresByIdFilm() {
        Film film = new Film("Film 1", "Description 1", LocalDate.of(1990, 1, 1),
                150, mpaStorage.get(1L), List.of(genreStorage.get(1L), genreStorage.get(3L)));
        Film newFilm = filmStorage.create(film);
        film.setId(newFilm.getId());
        filmGenreStorage.createFilmGenre(film);

        List<Long> savedIdsGenres = filmGenreStorage.getIdsGenreByFilm(newFilm.getId());
        List<Long> genres = List.of(1L, 3L);

        Assertions.assertThat(savedIdsGenres)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(genres);
    }
}