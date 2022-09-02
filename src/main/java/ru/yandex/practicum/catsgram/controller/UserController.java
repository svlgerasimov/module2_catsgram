package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    Map<String, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        String email = user.getEmail();
        validateEmail(email);
        if (users.containsKey(email)) {
            throw new UserAlreadyExistException("User with this email already exists");
        }
        users.put(email, user);
        return user;
    }

    @PutMapping
    public User createOrReplace(@RequestBody User user) {
        String email = user.getEmail();
        validateEmail(email);
        users.put(email, user);
        return user;
    }

    private void validateEmail(String email) {
        if (Objects.isNull(email) || email.equals("")) {
            throw new InvalidEmailException("Invalid email: " + email);
        }
    }
}
