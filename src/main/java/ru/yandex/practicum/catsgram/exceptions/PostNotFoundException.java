package ru.yandex.practicum.catsgram.exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(final String message) {
        super(message);
    }
}
