package ru.anafro.quark.server.utils.collections;

import ru.anafro.quark.server.utils.exceptions.UtilityClassInstantiationException;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Lists class contains uncategorized utility methods for
 * list manipulating. See static methods descriptions to learn more.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see Lists#join(List)
 * @since Quark 1.1
 */
public final class Lists {

    private static final String DEFAULT_DELIMITER = ", ";

    /**
     * This private constructor of Lists class <strong>MUST NOT</strong> be ever
     * called, because Lists is a utility class. Use static methods declared inside.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    private Lists() {
        throw new UtilityClassInstantiationException(getClass());
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
     * @param <T>        a type of list.
     * @return a list joined with a separator between each element.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public static <T> String join(List<? extends T> collection, String separator) {
        TextBuffer joinedContainer = new TextBuffer();

        for (int index = 0; index < collection.size(); index++) {
            joinedContainer.append(collection.get(index).toString());

            if (index != collection.size() - 1) {
                joinedContainer.append(separator);
            }
        }

        return joinedContainer.extractContent();
    }

    /**
     * Joins a list with a comma as a separator between each element.
     *
     * @param collection a list to join.
     * @param <T>        a list type.
     * @return a list joined with a comma between each element.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public static <T> String join(List<? extends T> collection) {
        return join(collection, DEFAULT_DELIMITER);
    }

    public static <T> ArrayList<T> empty() {
        return new ArrayList<>();
    }

    public static <T> String join(List<? extends T> collection, Function<T, String> presentation) {
        return join(collection.stream().map(presentation).toList());
    }

    public static <T> Optional<T> tryGet(ArrayList<T> list, int index) {
        if (Collections.isInvalidIndex(list, index)) {
            return Optional.empty();
        }

        return Optional.ofNullable(list.get(index));
    }

    public static <T, K> int indexOfKey(List<T> list, K key, Function<T, ?> keyGetter) {
        return list.stream().map(keyGetter).toList().indexOf(key);
    }

    public static <A, B> void forEachZipped(List<A> first, List<B> second, BiConsumer<A, B> action) {
        for (int i = 0; i < Math.min(first.size(), second.size()); i++) {
            action.accept(first.get(i), second.get(i));
        }
    }
}
