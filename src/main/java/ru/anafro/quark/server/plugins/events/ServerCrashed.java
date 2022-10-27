package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.networking.Server;

public class ServerCrashed extends Event {
    private final Server server;
    private final Throwable reason;

    public ServerCrashed(Server server, Throwable reason) {
        this.server = server;
        this.reason = reason;
    }

    public Server getServer() {
        return server;
    }

    public Throwable getReason() {
        return reason;
    }
}
