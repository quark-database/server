package ru.anafro.quark.server.utils.arrays;

public final class Arrays {
    private Arrays() {
        // Preventing creating utility class instance.
    }

    public static boolean contains(Object[] array, Object value) {
        for(Object element : array) {
            if(element.equals(value)) {
                return true;
            }
        }

        return false;
    }
}
