package ru.yandex.practicum.catsgram.exceptions;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(final String message) {
        super(message);
    }
}
