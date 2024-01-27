package ru.anafro.quark.server.networking.middlewares;

import ru.anafro.quark.server.networking.MiddlewareResponse;
import ru.anafro.quark.server.networking.Request;

public class TokenMiddleware extends Middleware {

    @Override
    public MiddlewareResponse handleRequest(Request request) {
        if (request.doesntHave("token")) {
            return MiddlewareResponse.deny("Token Is Required");
        }

        return MiddlewareResponse.pass();
    }
}
