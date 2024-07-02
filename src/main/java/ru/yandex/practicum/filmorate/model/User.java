package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    private long id;
    @NotEmpty
    @Email
    @NonNull
    private String email;
    @NonNull
    private String login;
    private String name;
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}

