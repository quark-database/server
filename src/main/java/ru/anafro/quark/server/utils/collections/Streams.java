package ru.anafro.quark.server.utils.collections;

import java.util.ArrayList;
import java.util.stream.Stream;

public final class Streams {
    private Streams() {
    }

    public static <T> ArrayList<T> toModifiableList(Stream<T> stream) {
        return new ArrayList<>(stream.toList());
    }
}
