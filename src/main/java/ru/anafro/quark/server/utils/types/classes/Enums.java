package ru.anafro.quark.server.utils.types.classes;

import ru.anafro.quark.server.utils.strings.Strings;

import static ru.anafro.quark.server.utils.arrays.Arrays.map;

public final class Enums {
    private Enums() {
    }

    @SafeVarargs
    public static <T extends Enum<T>> String join(T... values) {
        return String.join(", ", map(values, Enum::name));
    }

    public static <T extends Enum<T>> String getDisplayName(T value) {
        return value.name().toLowerCase().replace('_', ' ').transform(Strings::capitalize);
    }
}
