package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private int filmId = 1;
    private UserStorage userStorage;

    private int generateId() {
        return filmId++;
    }


    @Override
    public Film findById(long userId) {
        for (Map.Entry<Long, Film> entry : films.entrySet()) {
            if (entry.getKey() == userId) {
                return entry.getValue();
            }
        }
        throw new ValidationException("Film not found for id: " + userId);
    }

    @Override
    public Film updateFilm(Film film) {
        for (Map.Entry<Long, Film> entry : films.entrySet()) {
            if (entry.getValue().equals(film)) {
                films.put(entry.getKey(), film);
                return film;
            }
        }
        return null;
    }

    @Override
    public Film create(Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
        log.info("Фильм успешно добавлен: {}", film);
        return film;
    }

    @Override
    public Film removeLike(long filmId, long userId) {
        Film film = findById(userId);
        userStorage.getUserById(userId);
        film.removeLike(userId);
        return updateFilm(film);
    }

    @Override
    public List<Film> getTopFilms(int count) {

        List<Film> allFilms = getTopFilms(count);
        allFilms.sort(Comparator.comparingInt(Film::getLike).reversed());
        List<Film> bestFilms = allFilms.stream()
                .limit(count)
                .collect(Collectors.toList());
        return bestFilms;
    }


}
