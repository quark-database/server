package ru.anafro.quark.server.networking.middlewares;

import ru.anafro.quark.server.networking.MiddlewareResponse;
import ru.anafro.quark.server.networking.Request;

public abstract class Middleware {
    public abstract MiddlewareResponse handleRequest(Request request);
}
