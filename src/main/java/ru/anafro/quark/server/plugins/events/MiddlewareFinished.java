package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.networking.*;

public class MiddlewareFinished extends Event {
    private final Server server;
    private final Middleware middleware;
    private final ServerClient client;
    private final Request request;
    private final MiddlewareResponse response;

    public MiddlewareFinished(Server server, Middleware middleware, ServerClient client, Request request, MiddlewareResponse response) {
        this.server = server;
        this.middleware = middleware;
        this.client = client;
        this.request = request;
        this.response = response;
    }

    public Server getServer() {
        return server;
    }

    public Middleware getMiddleware() {
        return middleware;
    }

    public ServerClient getClient() {
        return client;
    }

    public Request getRequest() {
        return request;
    }

    public MiddlewareResponse getResponse() {
        return response;
    }
}
