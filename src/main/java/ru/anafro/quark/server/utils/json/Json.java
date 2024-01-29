package ru.anafro.quark.server.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.anafro.quark.server.utils.json.exceptions.JsonException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Optional;

public final class Json {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> Optional<T> tryDecode(String json, Class<T> type) {
        var object = gson.fromJson(json, type);
        return Optional.ofNullable(object);
    }

    public static <T> String encode(T object, Class<T> type) {
        try {
            var writer = gson.newJsonWriter(new StringWriter());
            writer.setIndent("\t");
            gson.toJson(object, type, writer);

            return writer.toString();
        } catch (IOException exception) {
            throw new JsonException(exception.getMessage());
        }
    }
}
