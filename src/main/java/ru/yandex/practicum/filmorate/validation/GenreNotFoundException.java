package ru.yandex.practicum.filmorate.validation;

public class GenreNotFoundException extends RuntimeException {

    public GenreNotFoundException(String message) {
        super(message);
    }

}
