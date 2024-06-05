package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Map;

public interface MpaStorage {
    Map<Long, Mpa> getAll();

    Mpa get(Long id);

    List<Long> getIds();
}

