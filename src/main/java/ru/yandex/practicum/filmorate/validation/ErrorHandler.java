package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.controller.*;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice(assignableTypes = {FilmController.class, UserController.class,
        GenreController.class, MpaController.class, FriendController.class})
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final List<ErrorResponse> errorResponses = e.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    log.warn(error.getDefaultMessage());
                    return new ErrorResponse(error.getDefaultMessage());
                })
                .collect(Collectors.toList());
        return new ValidationErrorResponse(errorResponses);
    }

    @ExceptionHandler({FilmNotFoundException.class, UserNotFoundException.class,
            MPANotFoundException.class, GenreNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final RuntimeException e) {
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleThrowable(final Throwable e) {
        return new ErrorResponse(
                "Произошла непредвиденная ошибка."
        );
    }

}

