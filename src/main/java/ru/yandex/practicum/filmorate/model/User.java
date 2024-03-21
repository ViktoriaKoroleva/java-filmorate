package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@Data
public class User {
    private Integer id;
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String name;
    private final LocalDate birthday;
    @NotBlank(message = "Логин не может быть пустым")
    private final String login;
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат Email")
    private final String email;
}
