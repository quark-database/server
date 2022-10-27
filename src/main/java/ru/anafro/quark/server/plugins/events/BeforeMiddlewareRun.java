package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.networking.Middleware;
import ru.anafro.quark.server.networking.Request;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.networking.ServerClient;

public class BeforeMiddlewareRun extends Event {
    private final Server server;
    private final ServerClient client;
    private final Request request;
    private final Middleware middleware;

    public BeforeMiddlewareRun(Server server, ServerClient client, Request request, Middleware middleware) {
        this.server = server;
        this.client = client;
        this.request = request;
        this.middleware = middleware;
    }

    public Server getServer() {
        return server;
    }

    public ServerClient getClient() {
        return client;
    }

    public Request getRequest() {
        return request;
    }

    public Middleware getMiddleware() {
        return middleware;
    }
}
