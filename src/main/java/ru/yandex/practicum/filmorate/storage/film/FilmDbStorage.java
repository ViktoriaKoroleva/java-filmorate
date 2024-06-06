package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    static Film createFilm(ResultSet rs, int rowNum) throws SQLException {
        return Film.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .duration(rs.getInt("duration"))
                .mpa(Mpa.builder().id(rs.getLong("mpa_id")).name(rs.getString("mpa_name")).build())
                .build();
    }

    @Override
    public Film create(Film film) {
        String sqlQuery = "insert into films (name, description, release_date, duration, mpa_id) values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId());
        String sqlQueryFilm = "select f.id, f.name, description, release_date, duration, mpa_id, m.name as mpa_name " +
                "from films f " +
                "left join mpa m " +
                "on f.mpa_id=m.id " +
                "where f.name= ? and description=? and release_date=? and duration=? ";
        Film filmFromBd = jdbcTemplate.queryForObject(sqlQueryFilm, FilmDbStorage::createFilm, film.getName(), film.getDescription(),
                film.getReleaseDate(), film.getDuration());
        return filmFromBd;
    }

    @Override
    public Film update(Film film) {
        String sqlQuery = "update films set name = ?, " +
                "description=?, " +
                "release_date=?, " +
                "duration=?, " +
                "mpa_id=? " +
                "where id= ?";
        jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        return getById(film.getId());
    }

    @Override
    public Map<Long, Film> getAll() {
        String sqlQuery = "select f.id, f.name, description, release_date, duration, mpa_id, m.name as mpa_name " +
                "from films f " +
                "left join mpa m " +
                "on f.mpa_id=m.id ";
        return jdbcTemplate.query(sqlQuery, FilmDbStorage::createFilmMap);
    }

    static Map createFilmMap(ResultSet rs) throws SQLException {
        HashMap<Long, Film> mapFilm = new HashMap<>();
        while (rs.next()) {
            mapFilm.put(rs.getLong("id"), Film.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .releaseDate(rs.getDate("release_date").toLocalDate())
                    .duration(rs.getInt("duration"))
                    .mpa(Mpa.builder().id(rs.getLong("mpa_id")).name(rs.getString("mpa_name")).build())
                    .build());
        }
        return mapFilm;
    }

    @Override
    public Film getById(Long id) {
        String sqlQuery = "select f.id, f.name, description, release_date, duration, mpa_id, m.name as mpa_name " +
                "from films f " +
                "left join mpa m " +
                "on f.mpa_id=m.id " +
                "where f.id= ?";
        Film film = jdbcTemplate.queryForObject(sqlQuery, FilmDbStorage::createFilm, id);
        return film;
    }

    @Override
    public List<Long> getIdFilms() {
        String sqlQuery = "select id from films ";
        List<Long> idFilms = jdbcTemplate.queryForList(sqlQuery, Long.class);
        return idFilms;
    }
}