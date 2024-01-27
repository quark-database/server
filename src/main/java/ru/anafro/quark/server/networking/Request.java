package ru.anafro.quark.server.networking;

import org.json.JSONException;
import org.json.JSONObject;
import ru.anafro.quark.server.database.language.Query;
import ru.anafro.quark.server.security.Token;

import java.util.Map;

public class Request {
    private final JSONObject data;
    private Query query = null;

    private Request(JSONObject data) {
        this.data = data;
    }

    @SafeVarargs
    public Request(Map.Entry<String, Object>... entries) {
        this(new JSONObject());

        for (var entry : entries) {
            var key = entry.getKey();
            var value = entry.getValue();

            data.put(key, value);
        }
    }

    public static Request empty() {
        return new Request();
    }

    public static Request createFromJson(String jsonString) {
        try {
            return new Request(new JSONObject(jsonString));
        } catch (JSONException exception) {
            return Request.empty();
        }
    }

    public String getString(String key) {
        return data.getString(key);
    }

    public Token getToken() {
        return new Token(data.getString("token"));
    }

    public boolean isQueryNotParsed() {
        return query == null;
    }

    public Query getQuery() {
        if (isQueryNotParsed()) {
            query = Query.make(getString("query"));
        }

        return query;
    }

    public boolean has(String key) {
        return data.has(key);
    }

    public boolean doesntHave(String key) {
        return !has(key);
    }
}
