package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
public class Genre {
    @NonNull
    private Long id;
    @NotBlank(message = "Ошибка Название не может быть пустым.")
    private final String name;

}
