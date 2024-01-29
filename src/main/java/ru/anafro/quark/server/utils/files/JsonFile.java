package ru.anafro.quark.server.utils.files;

import ru.anafro.quark.server.utils.json.Json;

import java.util.Optional;

public class JsonFile extends File {

    public JsonFile(String path) {
        super(path);
    }

    public <T> Optional<T> tryRead(Class<T> type) {
        return Json.tryDecode(this.read(), type);
    }

    public <T> void write(T object, Class<T> type) {
        write(Json.encode(object, type));
    }
}
