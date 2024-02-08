package ru.anafro.quark.server.utils.collections;

import java.util.Map;
import java.util.function.BiConsumer;

public final class Maps {
    private Maps() {
    }

    public static <K, V> void forEach(Map<K, V> map, BiConsumer<K, V> consumer) {
        for (var entry : map.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();

            consumer.accept(key, value);
        }
    }
}
