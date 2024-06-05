package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Map;

public interface GenreStorage {
    Map<Long, Genre> getAll();

    Genre get(Long id);

    List<Long> getIds();
}

