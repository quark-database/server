package ru.anafro.quark.server.utils.patterns;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.utils.collections.Lists;
import ru.anafro.quark.server.utils.objects.Nulls;
import ru.anafro.quark.server.utils.patterns.exceptions.ObjectAlreadyExistsInRegistryException;
import ru.anafro.quark.server.utils.patterns.exceptions.ObjectIsMissingInRegistryException;
import ru.anafro.quark.server.utils.strings.StringSimilarityFinder;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * A named object registry can be used to store the same object type inside.
 * But the difference from any collection is that any object has a name. Implement
 * the {@link NamedObjectsList#getNameOf(Object)} to let the registry know
 * how to name all the objects you will put into the registry.
 *
 * @param <E> an object type.
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see NamedObjectsList#get(String)
 * @see NamedObjectsList#has(String)
 * @see NamedObjectsList#asList()
 * @since Quark 1.1
 */
public abstract class NamedObjectsList<E> implements Iterable<E> {
    protected final List<E> elements;

    /**
     * Creates a new named object registry with <code>objectsToRegister</code> objects.
     *
     * @param objectsToRegister objects to register.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see NamedObjectsList#get(String)
     * @see NamedObjectsList#has(String)
     * @see NamedObjectsList#asList()
     * @since Quark 1.1
     */
    @SafeVarargs
    public NamedObjectsList(E... objectsToRegister) {
        elements = Collections.synchronizedList(Lists.empty());

        for (var objectToRegister : objectsToRegister) {
            add(objectToRegister);
        }
    }

    /**
     * Determines the name of an object. You have to implement this
     * method inside your named objects registers like so:
     *
     * <pre>
     * {@code
     * @Override
     * protected abstract String getNameOf(User user) {
     *     return user.getName();
     * }
     * }
     * </pre>
     *
     * @param object an object to name
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see NamedObjectsList#get(String)
     * @see NamedObjectsList#has(String)
     * @see NamedObjectsList#asList()
     * @since Quark 1.1
     */
    protected abstract String getNameOf(E object);

    /**
     * Returns an object named with <code>name</code> string.
     * If there's no object inside this register, <code>null</code> will
     * be returned.
     *
     * @param name a name of finding object.
     * @return a found object or <code>null</code> if there's no object with such name.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see NamedObjectsList#has(String)
     * @see NamedObjectsList#asList()
     * @since Quark 1.1
     */
    public E get(String name) {
        for (var object : elements) {
            if (getNameOf(object).equals(name)) {
                return object;
            }
        }

        return null;
    }

    public Optional<E> tryGet(String name) {
        for (var object : elements) {
            if (getNameOf(object).equals(name)) {
                return Optional.of(object);
            }
        }

        return Optional.empty();
    }

    public <T extends Throwable> E getOrThrow(String name, Supplier<T> exception) throws T {
        if (doesntHave(name)) {
            throw exception.get();
        }

        return get(name);
    }

    public E getOrThrow(String name, String exceptionMessage) {
        return getOrThrow(name, () -> new ObjectIsMissingInRegistryException(exceptionMessage));
    }

    /**
     * Returns <code>true</code> if this registry has an object
     * with <code>name</code> name. Otherwise, if there is no object
     * with such name, <code>false</code> will be returned.
     *
     * @param name a name of finding object.
     * @return a boolean representing the object existence.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see NamedObjectsList#get(String)
     * @see NamedObjectsList#asList()
     * @since Quark 1.1
     */
    public boolean has(String name) {
        return get(name) != null;
    }

    /**
     * Returns <code>true</code> if this registry has no objects
     * with <code>name</code> name. Otherwise, if there is an object
     * with such name, <code>false</code> will be returned.
     *
     * @param name a name of finding object.
     * @return a boolean representing the object existence.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see NamedObjectsList#get(String)
     * @see NamedObjectsList#has(String)
     * @see NamedObjectsList#asList()
     * @since Quark 1.1
     */
    public boolean doesntHave(String name) {
        return !has(name);
    }

    /**
     * Adds a new object to the registry. If any object has the same
     * name as the passed object does, ObjectAlreadyExistsInRegistryException will be thrown.
     *
     * @param object an object to add to the registry.
     * @throws ObjectAlreadyExistsInRegistryException when object with such name is already present in this registry.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see NamedObjectsList#get(String)
     * @see NamedObjectsList#has(String)
     * @see NamedObjectsList#asList()
     * @since Quark 1.1
     */
    public void add(E object) {
        if (has(getNameOf(object))) {
            throw new ObjectAlreadyExistsInRegistryException(getNameOf(object), object.getClass());
        }

        elements.add(object);
    }

    /**
     * Adds new objects to the registry. If any object has the same
     * name as the passed object does, ObjectAlreadyExistsInRegistryException will be thrown.
     *
     * @param objects objects to add to the registry.
     * @throws ObjectAlreadyExistsInRegistryException when any object with such name is already present in this registry.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see NamedObjectsList#get(String)
     * @see NamedObjectsList#has(String)
     * @see NamedObjectsList#asList()
     * @since Quark 1.1
     */
    @SafeVarargs
    public final void add(E... objects) {
        for (var object : objects) {
            this.add(object);
        }
    }

    @SafeVarargs
    public final void supplement(E... objects) {
        for (var object : objects) {
            if (has(getNameOf(object))) {
                continue;
            }

            add(object);
        }
    }

    /**
     * Returns the register objects iterator. Appending order is guaranteed.
     *
     * @return the register objects iterator.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see NamedObjectsList#get(String)
     * @see NamedObjectsList#has(String)
     * @see NamedObjectsList#asList()
     * @since Quark 1.1
     */
    @NotNull
    @Override
    public Iterator<E> iterator() {
        return elements.iterator();
    }

    /**
     * Returns the list of registered objects.
     *
     * @return the list of objects registered in this registry.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see NamedObjectsList#get(String)
     * @see NamedObjectsList#has(String)
     * @since Quark 1.1
     */
    public List<E> asList() {
        return elements;
    }

    public E suggest(String objectName) {
        double maximalSimilarity = Double.NEGATIVE_INFINITY;
        E theMostSimilarObject = null;

        for (var currentObject : this) {
            double currentSimilarity = StringSimilarityFinder.findSimilarity(objectName, Nulls.nullByDefault(theMostSimilarObject, this::getNameOf));

            if (currentSimilarity > maximalSimilarity) {
                maximalSimilarity = currentSimilarity;
                theMostSimilarObject = currentObject;
            }
        }

        return theMostSimilarObject;
    }

    public E getFirst() {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("The registry is empty.");
        }

        return elements.getFirst();
    }

    protected boolean isEmpty() {
        return elements.isEmpty();
    }
}
