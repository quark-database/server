package ru.anafro.quark.server.utils.containers;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Lists class contains uncategorized utility methods for
 * list manipulating. See static methods descriptions to learn more.
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    Lists#join(List)
 */
public final class Lists {

    /**
     * This private constructor of Lists class <strong>MUST NOT</strong> be ever
     * called, because Lists is a utility class. Use static methods declared inside.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    private Lists() {
        throw new CallingUtilityConstructorException(getClass());
    }

    /**
     * Joins a list to a string with a separator between each element.
     *
     * <pre>
     * {@code
     * var entities = List.of("anafro", "java", "norway", "dogs");
     *
     * // anafro + java + norway + dogs = love
     * Lists.join(entities, " + ") + " = love";
     * }
     * </pre>
     *
     * @param collection a list to join.
     * @param separator  a separator that will be put between each element, <i>but not at the end of the string</i>.
     * @return           a list joined with a separator between each element.
     * @param <T>        a type of list.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public static <T> String join(List<? extends T> collection, String separator) {
        TextBuffer joinedContainer = new TextBuffer();

        for(int index = 0; index < collection.size(); index++) {
            joinedContainer.append(collection.get(index));

            if(index != collection.size() - 1) {
                joinedContainer.append(separator);
            }
        }

        return joinedContainer.extractContent();
    }

    /**
     * Joins a list with a comma as a separator between each element.
     *
     * @param collection a list to join.
     * @return           a list joined with a comma between each element.
     * @param <T>        a list type.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public static <T> String join(List<? extends T> collection) {
        return join(collection, ", ");
    }

    public static <T> ArrayList<T> empty() {
        return new ArrayList<>();
    }

    public static <T> String joinPresentations(List<? extends T> collection, Function<T, String> presentation) {
        return join(collection.stream().map(presentation).toList());
    }
}
