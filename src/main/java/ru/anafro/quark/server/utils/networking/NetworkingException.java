package ru.anafro.quark.server.utils.networking;

import ru.anafro.quark.server.exceptions.QuarkException;

public class NetworkingException extends QuarkException {

    public NetworkingException(String message) {
        super(message);
    }
}
