package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
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
    @NotBlank(message = "Длина строки описания не должна быть больше 200 символов")
    private final String description;
    @Positive(message = "Продолжительность фильма не может быть отрицательной")
    private final Integer duration;
    private final LocalDate releaseDate;
}
