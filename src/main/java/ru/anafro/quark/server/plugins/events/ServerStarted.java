package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.networking.Server;

public class ServerStarted extends Event {
    private final Server server;

    public ServerStarted(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }
}
