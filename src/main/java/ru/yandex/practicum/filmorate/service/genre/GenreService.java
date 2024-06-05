package ru.yandex.practicum.filmorate.service.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GenreService {
    private GenreStorage genreStorage;

    @Autowired
    private GenreService(@Qualifier("genreDbStorage") GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public List<Genre> getAll() {
        return new ArrayList<>(genreStorage.getAll().values());
    }

    public Genre get(Long id) {
        Map<Long, Genre> genres = genreStorage.getAll();
        if (genres.containsKey(id)) {
            return genres.get(id);
        } else {
            throw new ValidationException("В бд нет жанра с id=" + id);
        }
    }
}

