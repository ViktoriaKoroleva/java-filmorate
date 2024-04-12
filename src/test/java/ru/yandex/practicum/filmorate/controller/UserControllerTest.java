package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.time.LocalDate;

class UserControllerTest {

    @Test
    public static void testUserWithoutEmail() {
        UserController controller = new UserController();
        User user = User.builder()
                .email("")
                .name("Vlad")
                .login("vladVik")
                .birthday(LocalDate.parse("2004-09-05"))
                .build();

        try {
            controller.createUser(user);
            System.out.println("Test failed: User without email was added.");
        } catch (ValidationException e) {
            System.out.println("Test passed: User without email was not added.");
        }
    }

    @Test
    public static void testUserWithoutName() {
        UserController controller = new UserController();
        User user = User.builder()
                .email("vlad@yandex.ru")
                .name("") // Invalid name
                .login("vladVik")
                .birthday(LocalDate.parse("2003-04-05"))
                .build();

        try {
            User newUser = controller.createUser(user);
            if (!newUser.getName().equals("vladVik")) {
                System.out.println("Test failed: User without name was not added.");
            } else {
                System.out.println("Test passed: User without name was added.");
            }
        } catch (ValidationException e) {
            System.out.println("Test failed: User without name was not added.");
        }
    }

    @Test
    public static void testUserWithoutLogin() {
        UserController controller = new UserController();
        User user = User.builder()
                .email("vlad@yandex.ru")
                .name("Vlad")
                .login(" ")
                .birthday(LocalDate.parse("2003-04-05"))
                .build();

        try {
            controller.createUser(user);
            System.out.println("Test failed: User without login was added.");
        } catch (ValidationException e) {
            System.out.println("Test passed: User without login was not added.");
        }
    }

}