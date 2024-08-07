package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.validation.FilmNotFoundException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Primary
@RequiredArgsConstructor
@Slf4j
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public Optional<Film> createFilm(Film film) {
        String sqlQuery = "insert into films(film_name, description, release_date, duration, rate, rating_mpa_id) " +
                " values (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getRate());
            stmt.setLong(6, film.getMpa().getId());

            return stmt;
        }, keyHolder);

        film.setId(keyHolder.getKey().longValue());
        film.setMpa(getRatingMPAById(film.getMpa().getId()).get());
        Film updateFilm = updateGenres(film);
        return Optional.of(updateFilm);
    }

    public Optional<Film> updateFilm(Film film) {
        if (findFilmById(film.getId()) == null) {
            return null;
        }
        String sqlQuery = "update films set film_name = ?, description = ?, release_date = ?, " +
                "duration = ?, rate = ?, rating_mpa_id = ? where film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getRate(), film.getMpa().getId(), film.getId());

        film.setMpa(getRatingMPAById(film.getMpa().getId()).get());
        deleteGenres(film.getId());
        film = updateGenres(film);
        return Optional.of(film);
    }

    public boolean deleteFilm(Film film) {
        if (findFilmById(film.getId()).isEmpty()) {
            return false;
        }
        String sqlQuery = "delete from films where film_id = ?";
        return jdbcTemplate.update(sqlQuery, film.getId()) > 0;
    }

    public List<Film> findFilms() {
        String sqlQuery = "select * from films";
        return jdbcTemplate.query(sqlQuery, this::makeFilm);
    }

    public Optional<Film> findFilmById(long filmId) {
        String sqlQuery = "select * from films where film_id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sqlQuery, this::makeFilm, filmId));
        } catch (EmptyResultDataAccessException e) {
            log.warn("Фильм № {} не найден", filmId);
            throw new FilmNotFoundException(String.format("Фильм № %d не найден", filmId));
        }
    }

    public List<Genre> getAllGenres() {
        String sqlQuery = "select * from genres";
        return jdbcTemplate.query(sqlQuery, this::makeGenre);
    }

    public Optional<Genre> getGenreById(long genreId) {
        String sqlQuery = "select * from genres where genre_id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sqlQuery, this::makeGenre, genreId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Mpa> getAllRatingMPAs() {
        String sqlQuery = "select * from rating_mpa";
        return jdbcTemplate.query(sqlQuery, this::makeRatingMPA);
    }

    public Optional<Mpa> getRatingMPAById(long ratingMPAId) {
        String sqlQuery = "select * from rating_mpa where rating_mpa_id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sqlQuery, this::makeRatingMPA, ratingMPAId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private void createGenre(Long filmId, Genre genre) {
        String sqlQuery = "insert into film_genre(film_id, genre_id) " +
                " values (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, genre.getId());
    }

    private Film updateGenres(Film film) {
        film.setGenres(film.getGenres().stream()
                .map(genre -> getGenreById(genre.getId()).get())
                .sorted(Comparator.comparing(Genre::getId))
                .collect(Collectors.toSet()));
        for (Genre genre : film.getGenres()) {
            createGenre(film.getId(), genre);
        }
        return film;
    }

    private void deleteGenres(Long filmId) {
        String sqlQuery = "delete from film_genre where film_id = ?";
        jdbcTemplate.update(sqlQuery, filmId);
    }

    private List<Genre> findGenresByFilmId(Long filmId) {
        String sqlQuery = "select * from film_genre as fg " +
                "join genres as g on g.genre_id = fg.genre_id " +
                "where fg.film_id = ? order by genre_id";
        return jdbcTemplate.query(sqlQuery, this::makeGenre, filmId);
    }

    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film(rs.getString("film_name"), rs.getString("description"),
                rs.getDate("release_date").toLocalDate());
        film.setId(rs.getLong("film_id"));
        film.setDuration(rs.getInt("duration"));
        film.setRate(rs.getInt("rate"));
        film.setGenres(new HashSet<>(findGenresByFilmId(film.getId())));
        film.setMpa(getRatingMPAById(rs.getLong("rating_mpa_id")).get());
        return film;
    }

    private Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getLong("genre_id"), rs.getString("genre_name"));
    }

    private Mpa makeRatingMPA(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(rs.getLong("rating_mpa_id"),
                rs.getString("rating_mpa_name"), rs.getString("description"));
    }

}