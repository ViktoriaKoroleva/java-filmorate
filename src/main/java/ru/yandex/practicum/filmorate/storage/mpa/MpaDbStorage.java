package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Map<Long, Mpa> getAll() {
        String sqlQuery = "select * from mpa";
        return jdbcTemplate.query(sqlQuery, MpaDbStorage::createMpaMap);
    }

    @Override
    public Mpa get(Long id) {
        String sqlQuery = "select * from mpa where id= ?";
        Mpa mpa = jdbcTemplate.queryForObject(sqlQuery, MpaDbStorage::createMpa, id);
        return mpa;
    }

    @Override
    public List<Long> getIds() {
        String sqlQuery = "select id from mpa ";
        List<Long> mpaIds = jdbcTemplate.queryForList(sqlQuery, Long.class);
        return mpaIds;
    }

    static Mpa createMpa(ResultSet rs, int rowNum) throws SQLException {
        return Mpa.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }

    static Map createMpaMap(ResultSet rs) throws SQLException {
        Map<Long, Mpa> mpas = new HashMap<>();
        while (rs.next()) {
            mpas.put(rs.getLong("id"), Mpa.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .build());
        }
        return mpas;
    }
}
