package ru.anafro.quark.server.networking;

import org.json.JSONObject;

@FunctionalInterface
public interface Middleware {
    MiddlewareResponse filter(Request request);
}
