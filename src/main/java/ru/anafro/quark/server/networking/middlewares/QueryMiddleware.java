package ru.anafro.quark.server.networking.middlewares;

import ru.anafro.quark.server.networking.MiddlewareResponse;
import ru.anafro.quark.server.networking.Request;

public class QueryMiddleware extends Middleware {

    @Override
    public MiddlewareResponse handleRequest(Request request) {
        if (request.doesntHave("query")) {
            return MiddlewareResponse.deny("Query Is Required");
        }

        return MiddlewareResponse.pass();
    }
}
