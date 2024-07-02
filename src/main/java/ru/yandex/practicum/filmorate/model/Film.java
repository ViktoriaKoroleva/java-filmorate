package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

@Data
public class Film {

    private long id;

    @NonNull
    @NotBlank(message = "Ошибка Название не может быть пустым.")
    private String name;
    @NonNull
    @Size(max = 200)
    private String description;
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    @NotNull
    private Mpa mpa;
    @Positive(message = "Ошибка Продолжительность фильма должна быть положительной.")
    private int duration;

    private int rate;
    private Set<Long> likes = new HashSet<>();
    private Set<Genre> genres = new TreeSet<>(Comparator.comparingLong(Genre::getId));

    @JsonSetter
    public void setGenres(Set<Genre> genres) {
        this.genres.clear();
        this.genres.addAll(genres);
    }

}
