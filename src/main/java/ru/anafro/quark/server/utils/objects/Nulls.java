package ru.anafro.quark.server.utils.objects;

import ru.anafro.quark.server.utils.exceptions.UtilityClassInstantiationException;

import java.util.function.Function;

/**
 * {@link Nulls} class provides a powerful methods for handling
 * null values in your code. See examples to understand better:
 *
 * <pre>
 * {@code
 * User user = null; // Imagine you got null value from somewhere
 *
 * // But you have to display something about them inside your code:
 * logger.info(user.name()); // Fails with java.lang.NullPointerException
 *
 * // Try this instead:
 * //   "<no name>" if user is null;
 * //   user.name() if user is present.
 * logger.info(evalOrDefault(user, User::name, "<no name>");
 * }
 * </pre>
 * <p>
 * It's totally fine to static import methods to shorten your code.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public final class Nulls {

    /**
     * This private constructor of Nulls class <strong>MUST NOT</strong> be ever
     * called, because Nulls is a utility class. Use static methods declared inside.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    private Nulls() {
        throw new UtilityClassInstantiationException(getClass());
    }

    /**
     * Returns an <code>object</code> if it is present (not null).
     * Otherwise, <code>defaultValue</code> will be returned.
     *
     * @param object       an object to check for the null value.
     * @param defaultValue a default value that will be returned if <code>object == null</code>
     * @param <T>          the type of both objects
     * @return (see the description above)
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public static <T> T byDefault(T object, T defaultValue) {
        return object == null ? defaultValue : object;
    }

    /**
     * Evaluates the lambda <code>ifNotNull</code> on the object <code>nullableObject</code>
     * if it is not null. Otherwise, if object is null, <code>ifNull</code> object will be returned.
     *
     * @param nullableObject an object to check for the null value.
     * @param ifNotNull      a function that will be invoked on <code>nullableObject</code> if it is present (not null).
     * @param ifNull         an object that will be returned if <code>nullableObject</code> is null.
     * @param <T>            a type of the checking object
     * @param <U>            a type of the returning object
     * @return (see description above)
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public static <T, U> U byDefault(T nullableObject, Function<T, U> ifNotNull, U ifNull) {
        if (nullableObject == null) {
            return ifNull;
        } else {
            return ifNotNull.apply(nullableObject);
        }
    }

    public static <T, U> U nullByDefault(T nullableObject, Function<T, U> ifNotNull) {
        return byDefault(nullableObject, ifNotNull, null);
    }
}
