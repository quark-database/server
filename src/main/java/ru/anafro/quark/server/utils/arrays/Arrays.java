package ru.anafro.quark.server.utils.arrays;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;

import java.util.Random;

/**
 * Arrays class contains uncategorized utility methods for
 * arrays manipulating. See static methods descriptions to learn more.
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    Arrays#random(Object[])
 */
public final class Arrays {
    private static final Random random = new Random();

    /**
     * This private constructor of Arrays class <strong>MUST NOT</strong> be ever
     * called, because Arrays is a utility class. Use static methods declared inside.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    Arrays
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
     * @see    Arrays
     */
    public static <T> T random(T[] array) { // TODO: It seems like this can fail in some cases. Please, read this code carefully to ensure that this code is fine or rewrite it if it doesn't
        return array[random.nextInt(array.length)];
    }

    /**
     * Returns <code>true</code> if <code>array</code> contains <code>value</code>.
     * Otherwise, <code>false</code> is returned.
     *
     * @param array an array where this method will be searching for the value.
     * @param value a value which will be searched in the array.
     * @return      (see the description)
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    Arrays
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
     * @see    Arrays
     */
    @SafeVarargs
    public static <T> T[] of(T... objects) {
        return objects;
    }
}
