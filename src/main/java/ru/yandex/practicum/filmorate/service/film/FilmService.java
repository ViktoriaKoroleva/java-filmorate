package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }


    public HashMap<Integer, Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film findById(long filmId) {
        return filmStorage.getFilmById(filmId);
    }


    public List<Film> getTopRatedFilms(int count) {
        List<Film> films = new ArrayList<>(filmStorage.getFilms().values());

        List<Film> sortedFilms = films.stream().sorted(Comparator.comparingInt(f -> -f.getLikes().size())).collect(Collectors.toList());

        return sortedFilms.subList(0, Math.min(count, sortedFilms.size()));
    }
}
