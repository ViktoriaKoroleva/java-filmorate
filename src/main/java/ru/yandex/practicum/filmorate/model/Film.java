package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
@Builder
public class Film {
    private Integer id;
    @NotBlank(message = "Название фильма не может быть пустым")
    private final String name;
    @Size(max = 200)
    private final String description;
    @Positive(message = "Продолжительность фильма не может быть отрицательной")
    private final Integer duration;
    private final LocalDate releaseDate;
}
