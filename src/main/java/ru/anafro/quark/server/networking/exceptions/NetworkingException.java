package ru.anafro.quark.server.networking.exceptions;

import ru.anafro.quark.server.QuarkException;

/**
 * NetworkingException is a base exception for every exception
 * in Quark networking. It must NOT been thrown, but extended
 * for the specific situations.
 */
public class NetworkingException extends QuarkException {
    public NetworkingException(String message) {
        super(message);
    }
}
