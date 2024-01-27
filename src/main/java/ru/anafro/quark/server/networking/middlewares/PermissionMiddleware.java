package ru.anafro.quark.server.networking.middlewares;

import ru.anafro.quark.server.networking.MiddlewareResponse;
import ru.anafro.quark.server.networking.Request;

public class PermissionMiddleware extends Middleware {

    @Override
    public MiddlewareResponse handleRequest(Request request) {
        var query = request.getQuery();
        var token = request.getToken();

        if (token.canNot(query)) {
            return MiddlewareResponse.deny("No Permission");
        }

        return MiddlewareResponse.pass();
    }
}
