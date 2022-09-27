package ru.anafro.quark.server.networking;

@FunctionalInterface
public interface Middleware {
    MiddlewareResponse filter(Request request);
}
