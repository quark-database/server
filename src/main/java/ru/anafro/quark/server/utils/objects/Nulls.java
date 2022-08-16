package ru.anafro.quark.server.utils.objects;

import java.util.function.Supplier;

public final class Nulls {
    private Nulls() {
        //
    }

    public static <T> T nullOrDefault(T object, T defaultValue) {
        return object == null ? defaultValue : object;
    }
    public static <T> T evalOrDefault(Object nullableObject, Supplier<T> ifNotNull, T ifNull) {
        if(nullableObject == null) {
            return ifNull;
        } else {
            return ifNotNull.get();
        }
    }
}
