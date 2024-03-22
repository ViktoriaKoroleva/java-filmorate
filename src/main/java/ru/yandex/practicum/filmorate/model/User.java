package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
public class User {
    private Integer id;

    private String name;
    private final LocalDate birthday;
    @NotBlank(message = "Логин не может быть пустым")
    private final String login;
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат Email")
    private final String email;
}
