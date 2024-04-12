package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

/**
 * Film
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film {
    private long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Set<Integer> likes;
    private Long duration;
    private int like;

    public void removeLike(long userId) {
        likes.remove(userId);
    }
}
