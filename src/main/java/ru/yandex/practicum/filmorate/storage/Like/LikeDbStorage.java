package ru.yandex.practicum.filmorate.storage.Like;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Repository
@Primary
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;

    public boolean like(Film film) {
        for (long idUser : film.getLikes()) {
            insertLike(idUser, film.getId());
        }
        return true;
    }

    private void insertLike(long userId, long filmId) {
        String sqlQuery = "insert into likes(user_id, film_id) values (?, ?)";
        jdbcTemplate.update(sqlQuery, userId, filmId);
    }

    public boolean dislike(Film film) {
        deleteLikesByFilmId(film.getId());
        return true;
    }

    private void deleteLikesByFilmId(long filmId) {
        String sqlQuery = "delete from likes where film_id = ?";
        jdbcTemplate.update(sqlQuery, filmId);
    }

    public List<Long> findLikes(Film film) {
        return findLikesByFilmId(film.getId());
    }

    private List<Long> findLikesByFilmId(long filmId) {
        String sqlQuery = "select user_id from likes where film_id = ?";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> rs.getLong("user_id"), filmId);
    }
}
