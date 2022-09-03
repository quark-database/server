package ru.anafro.quark.server.utils.patterns;

import ru.anafro.quark.server.utils.containers.Lists;
import ru.anafro.quark.server.utils.patterns.exceptions.ObjectAlreadyExistsInRegistryException;

import java.util.ArrayList;
import java.util.Iterator;

// TODO:
//  Add suggest(String)

/**
 * A named object registry can be used to store the same object type inside.
 * But the difference from any collection is that any object has a name. Implement
 * the {@link NamedObjectsRegistry#getNameOf(Object)} to let the registry know
 * how to name all the objects you will put into the registry.
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    NamedObjectsRegistry#get(String)
 * @see    NamedObjectsRegistry#has(String)
 * @see    NamedObjectsRegistry#asList()
 */
public abstract class NamedObjectsRegistry<E> implements Iterable<E> {
    protected final ArrayList<E> registeredObjects;

    /**
     * Creates a new named object registry with <code>objectsToRegister</code> objects.
     *
     * @param objectsToRegister objects to register.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    NamedObjectsRegistry#get(String)
     * @see    NamedObjectsRegistry#has(String)
     * @see    NamedObjectsRegistry#asList()
     */
    @SafeVarargs
    public NamedObjectsRegistry(E... objectsToRegister) {
        registeredObjects = Lists.empty();

        for(var objectToRegister : objectsToRegister) {
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
     * @param  object an object to name
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    NamedObjectsRegistry#get(String)
     * @see    NamedObjectsRegistry#has(String)
     * @see    NamedObjectsRegistry#asList()
     */
    protected abstract String getNameOf(E object);

    /**
     * Returns an object named with <code>name</code> string.
     * If there's no object inside this register, <code>null</code> will
     * be returned.
     *
     * @param name a name of finding object.
     * @return     a found object or <code>null</code> if there's no object with such name.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    NamedObjectsRegistry#get(String)
     * @see    NamedObjectsRegistry#has(String)
     * @see    NamedObjectsRegistry#asList()
     */
    public E get(String name) {
        for(var object : registeredObjects) {
            if(getNameOf(object).equals(name)) {
                return object;
            }
        }

        return null;
    }

    /**
     * Returns <code>true</code> if this registry has an object
     * with <code>name</code> name. Otherwise, if there is no object
     * with such name, <code>false</code> will be returned.
     *
     * @param name a name of finding object.
     * @return     a boolean representing the object existence.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    NamedObjectsRegistry#get(String)
     * @see    NamedObjectsRegistry#has(String)
     * @see    NamedObjectsRegistry#asList()
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
     * @return     a boolean representing the object existence.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    NamedObjectsRegistry#get(String)
     * @see    NamedObjectsRegistry#has(String)
     * @see    NamedObjectsRegistry#asList()
     */
    public boolean missing(String name) {
        return !has(name);
    }

    /**
     * Adds a new object to the registry. If any object has the same
     * name as the passed object do, ObjectAlreadyExistsInRegistryException will be thrown.
     *
     * @param object                                  an object to add to the registry.
     * @throws ObjectAlreadyExistsInRegistryException when object with such name is already present in this registry.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    NamedObjectsRegistry#get(String)
     * @see    NamedObjectsRegistry#has(String)
     * @see    NamedObjectsRegistry#asList()
     */
    public void add(E object) {
        if(has(getNameOf(object))) {
            throw new ObjectAlreadyExistsInRegistryException(getNameOf(object), object.getClass());
        }

        registeredObjects.add(object);
    }

    /**
     * Returns the register objects iterator. Appending order is guaranteed.
     *
     * @return the register objects iterator.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    NamedObjectsRegistry#get(String)
     * @see    NamedObjectsRegistry#has(String)
     * @see    NamedObjectsRegistry#asList()
     */
    @Override
    public Iterator<E> iterator() {
        return registeredObjects.iterator();
    }

    /**
     * Returns the count of registered objects inside this registry.
     *
     * @return the count of registered objects.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    NamedObjectsRegistry#get(String)
     * @see    NamedObjectsRegistry#has(String)
     * @see    NamedObjectsRegistry#asList()
     */
    public int count() {
        return registeredObjects.size();
    }

    /**
     * Returns the list of registered objects.
     *
     * @return the list of objects registered in this registry.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    NamedObjectsRegistry#get(String)
     * @see    NamedObjectsRegistry#has(String)
     * @see    NamedObjectsRegistry#asList()
     */
    public ArrayList<E> asList() {
        return registeredObjects;
    }
}
