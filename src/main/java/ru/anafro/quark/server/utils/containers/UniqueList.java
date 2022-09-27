package ru.anafro.quark.server.utils.containers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Unique list is a container with unique values inside.
 * The only difference between {@link UniqueList} and {@link ArrayList}
 * is that the first will ignore all the values that it already has.
 *
 * @param <T>
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    Lists
 * @deprecated UniqueList was added for an only reason - Quark is not only a portfolio project,
 *             but also a university year work. I have received a restriction: not to use any
 *             default containers except {@link ArrayList}. After passing the university review,
 *             all usages of {@link UniqueList} will be removed and replaced with {@link java.util.Set}.
 *             So please, do not use this class anywhere in your plugins. Thank you for your understanding :)
 */
@Deprecated
public class UniqueList<T> implements Iterable<T> {
    private final List<T> values;

    /**
     * Creates a new list with <code>values</code>.
     *
     * @param values initial values of this unique list.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @SafeVarargs
    public UniqueList(T... values) {
        this.values = Lists.empty();

        for(var value : values) {
            add(value);
        }
    }

    /**
     * Returns <code>true</code>, if this unique list has an
     * object passed to this method. Otherwise, if this method
     * does not contain this object, <code>false</code> will be returned.
     *
     * @param value a finding value.
     * @return      a boolean representing the object existence.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public boolean contains(T value) {
        return values.contains(value);
    }

    /**
     * Adds a new object to this unique list. If this
     * list already has this object, it will not be added.
     *
     * @param value an adding value.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public void add(T value) {
        if(!contains(value)) {
            values.add(value);
        }
    }

    /**
     * Returns an object at index <code>index</code>.
     *
     * @param index an index of an object that should be returned.
     * @return      an object at the index.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public T get(int index) { // TODO: It seems like this can fail in some cases. Please, read this code carefully to ensure that this code is fine or rewrite it if it doesn't
        return values.get(index);
    }

    /**
     * Returns an amount of objects inside this unique list.
     *
     * @return an amount of objects inside.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public int size() {
        return values.size();
    }

    /**
     * Returns <code>true</code> if this unique list is empty.
     * Otherwise, if this list contains objects, <code>false</code>
     * will be returned.
     *
     * @return a boolean represents the unique list emptiness.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public boolean isEmpty() {
        return values.isEmpty();
    }

    /**
     * Returns all the values stored in this unique list as an {@link ArrayList}.
     * Note that changing the returned list's content <strong>DOES NOT AFFECT</strong>
     * this unique list.
     *
     * @return an {@link ArrayList} with all values inside.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public ArrayList<T> asList() {
        return new ArrayList<>(values);
    }

    /**
     * Returns the list iterator of this unique list.
     *
     * @return the list iterator.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public Iterator<T> iterator() {
        return values.iterator();
    }
}
