package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> findAll() {
        Collection<User> users = userService.findAll();
        logger.debug("Текущее количество пользователей: {}", users.size());
        return users;
    }

    @GetMapping("/{email}")
    public User findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }


    @PostMapping
    public User create(@RequestBody User user) {
        logger.debug("Добавить пользователя: {}", user);
        return userService.create(user);
    }

    @PutMapping
    public User createOrUpdate(@RequestBody User user) {
        logger.debug("Обновить/добавить пользователя: {}", user);
        return userService.createOrUpdate(user);
    }

    private void validateEmail(String email) {
        if (Objects.isNull(email) || email.isBlank()) {
            throw new InvalidEmailException("Invalid email: " + email);
        }
    }
}
