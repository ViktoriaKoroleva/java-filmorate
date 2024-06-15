package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
public class Mpa {
    @NonNull
    private final Long id;

    @NotBlank(message = "Ошибка! Название не может быть пустым.")
    private final String name;

    @NotBlank(message = "Ошибка! Описание не может быть пустым.")
    private final String description;

}
