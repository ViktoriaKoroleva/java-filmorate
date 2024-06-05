package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Validated
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Film extends BaseUnit {
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Min(1)
    private int duration;
    private Mpa mpa;
    private List<Genre> genres;

}
