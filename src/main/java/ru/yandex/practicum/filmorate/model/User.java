package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String email;
    private LocalDate birthday;
    private Set<Long> friends;
    private String login;
    private String name;

    public void deleteFriend(long friendId) {
        friends.remove(friendId);
    }
}

