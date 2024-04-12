package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class FilmService {
    private final Map<Integer, Film> films = new HashMap<>();
    private final Map<Integer, Integer> likes = new HashMap<>();

    private FilmStorage filmStorage;


    public Film create(Film film) {
        isValidFilm(film);

        return filmStorage.create(film);
    }

    public void likeFilm(int filmId, int userId) {
        if (!films.containsKey(filmId)) {
            throw new IllegalArgumentException("Фильм с указанным ID не существует");
        }

        if (likes.containsKey(filmId)) {
            throw new IllegalStateException("Пользователь уже поставил лайк этому фильму");
        }

        likes.put(filmId, userId);
    }

    public Film removeLike(long filmId, long userId) {
        return filmStorage.removeLike(filmId, userId);
    }

    public Film findById(long filmId) {
        return filmStorage.findById(filmId);
    }

    public Film updateFilm(Film film) throws ValidationException {
        isValidFilm(film);
        return filmStorage.updateFilm(film);
    }

    public List<Film> getTopFilms() {
        Map<Film, Integer> filmLikesCount = new HashMap<>();
        for (Integer filmId : likes.keySet()) {
            Film film = films.get(filmId);
            filmLikesCount.put(film, filmLikesCount.getOrDefault(film, 0) + 1);
        }

        List<Map.Entry<Film, Integer>> sortedEntries = new ArrayList<>(filmLikesCount.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        List<Film> topFilms = new ArrayList<>();
        int count = 0;
        for (Map.Entry<Film, Integer> entry : sortedEntries) {
            topFilms.add(entry.getKey());
            count++;
            if (count == 10) {
                break;
            }
        }

        return topFilms;
    }

    private boolean isValidFilm(Film film) {
        return film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 27))
                && film.getReleaseDate().isBefore(LocalDate.now());
    }
}
