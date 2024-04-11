package ru.yandex.practicum.filmorate.service.film;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Service
public class FilmService {
    private final Map<Integer, Film> films = new HashMap<>();
    private final Map<Integer, Integer> likes = new HashMap<>();

    public Film addFilm(Film film) {
        films.put(film.getId(), film);
        return film;
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

    public void removeLike(int filmId) {
        likes.remove(filmId);
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
}
