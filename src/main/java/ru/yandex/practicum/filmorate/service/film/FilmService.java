package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.LikeStorage;
import ru.yandex.practicum.filmorate.storage.genre.FilmGenreStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private FilmStorage filmStorage;
    private LikeStorage likeStorage;
    private MpaStorage mpaStorage;
    private GenreStorage genreStorage;
    private FilmGenreStorage filmGenreStorage;

    @Autowired
    private FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage, LikeStorage likeStorage,
                        MpaStorage mpaStorage, GenreStorage genreStorage,
                        FilmGenreStorage filmGenreStorage) {
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
        this.mpaStorage = mpaStorage;
        this.genreStorage = genreStorage;
        this.filmGenreStorage = filmGenreStorage;

    }

    public Film create(Film film) {
        checkMpaId(film.getMpa().getId());
        Mpa mpa = mpaStorage.get(film.getMpa().getId());
        film.setMpa(mpa);

        if (film.getGenres() != null) {
            checkGenresId(film.getGenres());
        }
        Film filmNew = filmStorage.create(film);
        filmNew.setGenres(film.getGenres());
        filmGenreStorage.createFilmGenre(filmNew);
        filmNew.setGenres(getGenresByFilm(filmNew.getId()));
        return filmNew;
    }

    public Film update(Film film) {
        if (filmStorage.getIdFilms().contains(film.getId())) {
            film = filmStorage.update(film);
        } else {
            throw new ValidationException("Нет фильма с id = " + film.getId());
        }
        return film;
    }

    public List<Film> getAll() {
        try {
            Map<Long, Film> films = filmStorage.getAll();
            Map<Long, List<Long>> filmsGenres = filmGenreStorage.getAll();
            Map<Long, Genre> genres = genreStorage.getAll();

            List<Film> listFilms = new ArrayList<>();
            for (Long idFilm: films.keySet()) {
                Film film = films.get(idFilm);
                List<Long> idGenres = filmsGenres.get(idFilm);
                List<Genre> genresForThisFilm = new ArrayList<>();
                for (Long idGenre: idGenres) {
                    genresForThisFilm.add(genres.get(idGenre));
                }
                film.setGenres(genresForThisFilm);
                listFilms.add(film);
            }
            return listFilms;
        } catch (Exception e) {
            System.err.println("Ошибка при получении всех фильмов: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Внутренняя ошибка сервера при получении всех фильмов", e);
        }
    }

    public Film getById(Long id) {
        return filmStorage.getById(id);
    }

    public void addLike(Long filmId, Long userId) {
        filmStorage.getById(filmId);
        likeStorage.addLike(filmId, userId);
    }

    private boolean checkMpaId(Long id) {
        if (mpaStorage.getIds().contains(id)) {
            return true;
        } else {
            throw new ValidationException("Mpa_id = " + id + " не найден");
        }
    }

    public void deleteLike(Long idFilm, Long idUser) {
        likeStorage.deleteLike(idFilm, idUser);
    }

    private boolean checkGenresId(List<Genre> genres) {
        List<Long> idGenres = genreStorage.getIds();
        for (Genre genre : genres) {
            if (!idGenres.contains(genre.getId())) {
                throw new ValidationException("Genre_id = " + genre.getId() + " не найден");
            }
        }
        return true;
    }

    private List<Genre> getGenresByFilm(Long filmId) {
        List<Long> genreIds = filmGenreStorage.getIdsGenreByFilm(filmId);
        List<Genre> genres = new ArrayList<>();
        Map<Long, Genre> genresAll = genreStorage.getAll();
        for (Long idGenre : genreIds) {
            genres.add(genresAll.get(idGenre));
        }
        return genres;
    }


    public List<Film> getPopularFilms(Long count) {
        return likeStorage.getPopularFilms(count);
    }
}