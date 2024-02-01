package ru.anafro.quark.server.utils.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static ru.anafro.quark.server.utils.integers.Integers.between;

public final class Collections {
    private Collections() {
    }

    @SafeVarargs
    public static <T> List<T> list(T... elements) {
        return new ArrayList<>(List.of(elements));
    }

    public static List<Integer> list(int[] elements) {
        return Arrays.stream(elements).boxed().toList();
    }

    public static <T> List<T> emptyList() {
        return list();
    }

    public static boolean isValidIndex(Collection<?> collection, int index) {
        return between(0, index, collection.size() - 1);
    }

    public static boolean isInvalidIndex(Collection<?> collection, int index) {
        return !isValidIndex(collection, index);
    }

    public static <T> String join(Iterable<? extends T> iterable, Function<T, String> valueToString) {
        return join(iterable, valueToString, ", ");
    }

    public static <T> String join(Iterable<? extends T> iterable, Function<T, String> valueToString, String delimiter) {
        return StreamSupport.stream(iterable.spliterator(), false).map(valueToString).collect(Collectors.joining(delimiter));
    }

    public static <T> boolean equalsIgnoreOrder(Collection<T> first, Collection<T> second) {
        return first.size() == second.size() && first.containsAll(second) && second.containsAll(first);
    }
}
