package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Map<Long, Genre> getAll() {
        String sqlQuery = "select * from genres";
        List<Genre> genreList = jdbcTemplate.query(sqlQuery, GenreDbStorage::createGenre);
        return genreList.stream().collect(Collectors.toMap(Genre::getId, genre -> genre));
    }

    @Override
    public Genre get(Long id) {
        String sqlQuery = "select * from genres where id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, GenreDbStorage::createGenre, id);
    }

    @Override
    public List<Long> getIds() {
        String sqlQuery = "select id from genres";
        return jdbcTemplate.queryForList(sqlQuery, Long.class);
    }


    private static Genre createGenre(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}
