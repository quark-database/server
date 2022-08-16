package ru.anafro.quark.server.multithreading.exceptions;

import ru.anafro.quark.server.exceptions.QuarkException;

public class MultithreadingException extends QuarkException {
    public MultithreadingException(String message) {
        super(message);
    }
}
