package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Map getAll() {
        String sqlQuery = "select * from genres";
        return jdbcTemplate.query(sqlQuery, GenreDbStorage::createGenreMap);
    }

    @Override
    public Genre get(Long id) {
        String sqlQuery = "select * from genres where id= ?";
        Genre genres = jdbcTemplate.queryForObject(sqlQuery, GenreDbStorage::createGenre, id);
        return genres;
    }

    @Override
    public List<Long> getIds() {
        String sqlQuery = "select id from genres";
        List<Long> genreIds = jdbcTemplate.queryForList(sqlQuery, Long.class);
        return genreIds;
    }

    static Genre createGenre(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }

    static Map createGenreMap(ResultSet rs) throws SQLException {
        Map<Long, Genre> genres = new HashMap<>();
        while (rs.next()) {
            genres.put(rs.getLong("id"), Genre.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .build());
        }
        return genres;
    }
}
