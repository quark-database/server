package ru.anafro.quark.server.networking;

import org.json.JSONObject;
import ru.anafro.quark.server.database.language.InstructionResult;
import ru.anafro.quark.server.database.language.ResponseStatus;

public record Response(JSONObject json) {
    public static Response make(InstructionResult result) {
        return Response.makeEmpty()
                .set("status", result.responseStatus().name())
                .set("message", result.message())
                .set("time", result.milliseconds())
                .set("table", result.tableView().toJson());
    }

    public static Response error(ResponseStatus status, Exception exception) {
        return makeEmpty()
                .set("status", status.name())
                .set("message", exception.getMessage());
    }

    public static Response makeEmpty() {
        return new Response(new JSONObject());
    }

    public static Response syntaxError(Exception exception) {
        return error(ResponseStatus.SYNTAX_ERROR, exception);
    }

    public static Response serverError(Exception exception) {
        return error(ResponseStatus.SERVER_ERROR, exception);
    }

    public <T> Response set(String key, T value) {
        json.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return json.toString();
    }
}
