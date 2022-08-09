package ru.anafro.quark.server.utils.arrays;

import java.util.Random;

public final class Arrays {
    private static final Random random = new Random();

    private Arrays() {
        // Preventing creating utility class instance.
    }

    public static <T> T random(T[] array) {
        return array[random.nextInt(array.length)];
    }

    public static boolean contains(Object[] array, Object value) {
        for(Object element : array) {
            if(element.equals(value)) {
                return true;
            }
        }

        return false;
    }

    @SafeVarargs
    public static <T> T[] of(T... objects) {
        return objects;
    }
}
