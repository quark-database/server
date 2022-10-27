package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.networking.Server;

public class BeforeServerStarted extends Event {
    private Server server;

    public BeforeServerStarted(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }
}
