package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.networking.Server;

public class BeforeServerCrash extends Event {
    private final Server server;
    private final String errorMessage;

    public BeforeServerCrash(Server server, String errorMessage) {
        this.server = server;
        this.errorMessage = errorMessage;
    }

    public Server getServer() {
        return server;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
