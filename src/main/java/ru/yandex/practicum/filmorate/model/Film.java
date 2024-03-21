package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
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
    @NotBlank(message = "Описание фильма не может быть пустым")
    private final String description;
    @PositiveOrZero(message = "Продолжительность фильма не может быть отрицательной")
    private final Integer duration;
    private final LocalDate releaseDate;
}
