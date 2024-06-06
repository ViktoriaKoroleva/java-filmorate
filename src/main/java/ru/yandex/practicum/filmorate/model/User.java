package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@SuperBuilder
@Validated
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class User extends BaseUnit {
    @NotEmpty
    @Email
    private String email;
    @NotBlank
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
}

