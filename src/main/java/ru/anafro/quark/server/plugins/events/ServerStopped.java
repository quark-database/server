package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.networking.Server;

public class ServerStopped extends Event {
    private final Server server;

    public ServerStopped(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }
}
