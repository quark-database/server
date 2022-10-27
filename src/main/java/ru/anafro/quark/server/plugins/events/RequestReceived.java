package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.networking.Request;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.networking.ServerClient;

public class RequestReceived extends Event {
    private final Server server;
    private final Request request;

    public RequestReceived(Server server, Request request) {
        this.server = server;
        this.request = request;
    }

    public Server getServer() {
        return server;
    }

    public Request getRequest() {
        return request;
    }
}
