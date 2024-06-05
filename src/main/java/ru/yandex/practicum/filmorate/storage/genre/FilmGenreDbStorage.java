package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FilmGenreDbStorage implements FilmGenreStorage {
    private final JdbcTemplate jdbcTemplate;

    static Map createFilmGenreMap(ResultSet rs) throws SQLException {
        HashMap<Long, List<Long>> mapFilmGenre = new HashMap<>();
        while (rs.next()) {
            if (mapFilmGenre.containsKey(rs.getLong("film_id"))) {
                List<Long> genres = new ArrayList<>(mapFilmGenre.get(rs.getLong("film_id")));
                genres.add(rs.getLong("genre_id"));
                mapFilmGenre.put(rs.getLong("film_id"), genres);
            } else {
                mapFilmGenre.put(rs.getLong("film_id"), List.of(rs.getLong("genre_id")));
            }
        }
        return mapFilmGenre;
    }

    public List<Long> getIdsGenreByFilm(Long id) {
        String sqlQuery = "select genre_id from film_genres where film_id= ?";
        List<Long> genres = jdbcTemplate.queryForList(sqlQuery, Long.class, id);
        return genres;
    }

    @Override
    public Map getAll() {
        String sqlQuery = "select film_id, genre_id from film_genres ";
        Map<Long, List<Long>> genres = jdbcTemplate.query(sqlQuery, FilmGenreDbStorage::createFilmGenreMap);
        return genres;
    }

    @Override
    public void createFilmGenre(Film film) {
        for (Genre genre : film.getGenres()) {
            if (!(getIdsGenreByFilm(film.getId()).contains(genre.getId()))) {
                String sqlQueryFilmGenre = "insert into film_genres values (?, ?)";
                jdbcTemplate.update(sqlQueryFilmGenre, film.getId(), genre.getId());
            }
        }
    }
}
