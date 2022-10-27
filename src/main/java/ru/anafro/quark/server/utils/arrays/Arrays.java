package ru.anafro.quark.server.utils.arrays;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Arrays class contains uncategorized utility methods for
 * arrays manipulating. See static methods descriptions to learn more.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see     Arrays#random(Object[])
 */
public final class Arrays {
    /**
     * The random generator for {@link java.util.Arrays} class.
     * @since Quark 1.1
     */
    private static final Random random = new Random();

    /**
     * This private constructor of Arrays class <strong>MUST NOT</strong> be ever
     * called, because Arrays is a utility class. Use static methods declared inside.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    private Arrays() {
        throw new CallingUtilityConstructorException(getClass());
    }

    /**
     * Returns a random element from the array.
     *
     * @param     array an array where a random element will be picked from.
     * @return    a random element from this array.
     * @param <T> a type of array.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public static <T> T random(T[] array) { // TODO: It seems like this can fail in some cases. Please, read this code carefully to ensure that this code is fine or rewrite it if it doesn't
        return array[random.nextInt(array.length)];
    }

    /**
     * Returns {@code true} if {@code array} contains {@code value}.
     * Otherwise, {@code false} is returned.
     *
     * @param array an array where this method will be searching for the value.
     * @param value a value which will be searched in the array.
     * @return      (see the description)
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public static boolean contains(Object[] array, Object value) {
        for(Object element : array) {
            if(element.equals(value)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Creates an array of objects passed to this method.
     *
     * @param objects objects that will be in an array.
     * @return        an array of objects passed.
     * @param <T>     a type of objects.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @SafeVarargs
    public static <T> T[] of(T... objects) {
        return objects;
    }

    public static <T> T[] concat(T[] first, T[] second) {
        return Stream.concat(java.util.Arrays.stream(first), java.util.Arrays.stream(second))
                .toArray(size -> (T[]) Array.newInstance(first.getClass().getComponentType(), size));
    }
}
