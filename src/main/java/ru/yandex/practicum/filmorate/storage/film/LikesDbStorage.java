package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LikesDbStorage implements LikeStorage {

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

    public void addLike(Long idFilm, Long idUser) {
        String sqlQuery = "insert into likes (user_id, film_id) values (?, ?)";
        jdbcTemplate.update(sqlQuery, idUser, idFilm);
    }

    public void deleteLike(Long idFilm, Long idUser) {
        String sqlQuery = "delete from likes where user_id=? and film_id=? ";
        jdbcTemplate.update(sqlQuery, idUser, idFilm);
    }

    public List<Film> getPopularFilms(Long count) {
        String sqlQuery = "select f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id, m.name as mpa_name " +
                "from (select film_id, sum(user_id) as likes " +
                "from likes " +
                "group by film_id " +
                "order by sum(user_id) desc " +
                "limit ?) l " +
                "left join films f on l.film_id=f.id " +
                "left join mpa m on m.id=f.mpa_id ";
        return jdbcTemplate.query(sqlQuery, LikesDbStorage::createFilm, count);
    }
}
