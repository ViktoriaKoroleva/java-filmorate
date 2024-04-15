package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.util.*;

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
    public Film getFilmById(long userId) {
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
        films.put((long) film.getId(), film);
        log.info("Фильм успешно добавлен: {}", film);
        return film;
    }

    @Override
    public Film removeLike(int filmId, int userId) {
        Film film = getFilmById(userId);
        userStorage.getUserById(userId);
        userStorage.removeFriend(userId, filmId);
        return updateFilm(film);
    }

    @Override
    public List<Film> getTopFilms(int count) {
        List<Film> allFilms = new ArrayList<>(films.values());
        Collections.sort(allFilms, (film1, film2) -> Integer.compare(film2.getLikes().size(), film1.getLikes().size()));
        return allFilms.subList(0, Math.min(count, allFilms.size()));
    }

    @Override
    public HashMap<Integer, Film> getFilms() {
        HashMap<Integer, Film> filmMap = new HashMap<>();
        for (Map.Entry<Long, Film> entry : films.entrySet()) {
            filmMap.put(entry.getValue().getId(), entry.getValue());
        }
        return filmMap;
    }
}
