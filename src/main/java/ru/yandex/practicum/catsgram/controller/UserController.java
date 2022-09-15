package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final Map<String, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        logger.debug("Текущее количество пользователей: {}", users.size());
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        logger.debug("Добавить пользователя: {}", String.valueOf(user));
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
        logger.debug("Обновить/добавить пользователя: {}", String.valueOf(user));
        String email = user.getEmail();
        validateEmail(email);
        users.put(email, user);
        return user;
    }

    private void validateEmail(String email) {
        if (Objects.isNull(email) || email.isBlank()) {
            throw new InvalidEmailException("Invalid email: " + email);
        }
    }
}
