package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {

    private final Map<String, User> users = new HashMap<>();

    public Collection<User> findAll() {
        return users.values();
    }

    public User create(User user) {
        String email = user.getEmail();
        validateEmail(email);
        if (users.containsKey(email)) {
            throw new UserAlreadyExistException(String.format("User with email %s already exists", email));
        }
        users.put(email, user);
        return user;
    }

    public User createOrUpdate(User user) {
        String email = user.getEmail();
        validateEmail(email);
        users.put(email, user);
        return user;
    }

    public boolean containsUser(String email) {
        return !Objects.isNull(email) && users.containsKey(email);
    }

    public User findByEmail(String email) {
        User user = Objects.isNull(email) ? null : users.get(email);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException(String.format("User with email %s not found", email));
        }
        return user;
    }

    private void validateEmail(String email) {
        if (Objects.isNull(email) || email.isBlank()) {
            throw new InvalidEmailException("Invalid email: " + email);
        }
    }

}
