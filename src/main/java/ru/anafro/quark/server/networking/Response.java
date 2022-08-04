package ru.anafro.quark.server.networking;

import org.json.JSONObject;

public record Response(JSONObject data) {

    public static Response error(String message) {
        return create()
                .add("status", "ERROR")
                .add("message", message);
    }

    public static Response ok() {
        return create()
                .add("status", "OK");
    }
    public static Response create() {
        return new Response(new JSONObject());
    }

    public <T> Response add(String key, T value) {
        data.put(key, value);
        return this;
    }

    public Message toMessage() {
        return new Message(toString());
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
