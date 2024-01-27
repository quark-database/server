package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.networking.Client;
import ru.anafro.quark.server.networking.MiddlewareResponse;
import ru.anafro.quark.server.networking.Request;
import ru.anafro.quark.server.networking.middlewares.Middleware;

public class MiddlewareFinishedEvent extends Event {
    private final Middleware middleware;
    private final Client client;
    private final Request request;
    private final MiddlewareResponse response;

    public MiddlewareFinishedEvent(Middleware middleware, Client client, Request request, MiddlewareResponse response) {
        this.middleware = middleware;
        this.client = client;
        this.request = request;
        this.response = response;
    }

    public Middleware getMiddleware() {
        return middleware;
    }

    public Client getClient() {
        return client;
    }

    public Request getRequest() {
        return request;
    }

    public MiddlewareResponse getResponse() {
        return response;
    }
}
