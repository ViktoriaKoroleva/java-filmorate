package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@Validated
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class User extends BaseUnit {
    private final Set<Integer> friends = new HashSet<>();

    @NotBlank(message = "Электронная почта не может быть пустой.")
    @Email(message = "Неверный формат электронной почты.")
    private String email;

    @NotBlank(message = "Логин не может быть пустым.")
    @Pattern(regexp = "\\S+", message = "Логин не может содержать пробелы")
    private String login;

    private String name;

    @NotNull(message = "Дата рождения не может быть пустой")
    @Past(message = "Дата рождения не может быть в будущем.")
    private LocalDate birthday;
}

