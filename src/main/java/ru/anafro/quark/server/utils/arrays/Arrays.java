package ru.anafro.quark.server.utils.arrays;

import ru.anafro.quark.server.utils.exceptions.UtilityClassInstantiationException;
import ru.anafro.quark.server.utils.exceptions.UtilityException;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Arrays class contains uncategorized utility methods for
 * arrays manipulating. See static methods descriptions to learn more.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @see Arrays#pick(Object[])
 * @since Quark 1.1
 */
@SuppressWarnings("unchecked")
public final class Arrays {
    /**
     * The random generator for {@link java.util.Arrays} class.
     *
     * @since Quark 1.1
     */
    private static final Random random = new Random();

    /**
     * This private constructor of Arrays class <strong>MUST NOT</strong> be ever
     * called, because Arrays is a utility class. Use static methods declared inside.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    private Arrays() {
        throw new UtilityClassInstantiationException(getClass());
    }

    /**
     * Returns a random element from the array.
     *
     * @param array an array where a random element will be picked from.
     * @param <T>   a type of array.
     * @return a random element from this array.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public static <T> T pick(T[] array) { // TODO: It seems like this can fail in some cases. Please, read this code carefully to ensure that this code is fine or rewrite it if it doesn't
        return array[random.nextInt(array.length)];
    }

    /**
     * Returns {@code true} if {@code array} contains {@code value}.
     * Otherwise, {@code false} is returned.
     *
     * @param array an array where this method will be searching for the value.
     * @param value a value which will be searched in the array.
     * @return (see the description)
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public static boolean contains(Object[] array, Object value) {
        for (Object element : array) {
            if (element.equals(value)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Creates an array of elements passed to this method.
     *
     * @param elements elements that will be in an array.
     * @param <T>      a type of elements.
     * @return an array of elements passed.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public static <T> T[] array(T... elements) {
        return elements;
    }

    public static <T> T[] generate(Class<T> type, int length, Function<Integer, T> generator) {
        var array = (T[]) Array.newInstance(type, length);

        for (var index = 0; index < length; index += 1) {
            array[index] = generator.apply(index);
        }

        return array;
    }

    public static <T, R> R[] map(Class<R> targetType, T[] array, Function<T, R> mapper) {
        return generate(targetType, array.length, index -> mapper.apply(array[index]));
    }

    public static <T, R> R[] map(T[] array, Function<T, R> mapper) {
        return (R[]) Stream.of(array).map(mapper).toArray();
    }

    public static <T, R> R[] map(T[] array, BiFunction<T, Integer, R> mapper) {
        return (R[]) IntStream.range(0, array.length).mapToObj(index -> mapper.apply(array[index], index)).toArray();
    }

    public static <T> boolean allMatch(T[] array, Predicate<T> condition) {
        return Stream.of(array).allMatch(condition);
    }

    public static <T> T getLast(T[] array) {
        if (array.length == 0) {
            throw new UtilityException("Array is empty.");
        }

        return array[array.length - 1];
    }
}
