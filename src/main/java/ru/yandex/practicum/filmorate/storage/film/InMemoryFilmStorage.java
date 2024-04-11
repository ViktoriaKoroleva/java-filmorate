package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId = 1;

    private int generateId() {
        return filmId++;
    }

    @Override
    public List<Film> findAll() {
        log.info("Фильмов в коллекции: {}", films.size());
        return List.copyOf(films.values());
    }

    @Override
    public Film updateFilm(long id, Film film) {
        if (films.containsKey(id)) {
            films.put((int) id, film);
            return film;
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
    public boolean removeFilm(long id) {
        Film removedFilm = films.remove(id);
        if (removedFilm != null) {
            log.info("Фильм с ID {} был успешно удален", id);
            return true;
        } else {
            log.warn("Фильм с ID {} не найден", id);
            return false;
        }
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
