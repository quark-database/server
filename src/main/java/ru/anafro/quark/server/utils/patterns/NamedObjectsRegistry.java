package ru.anafro.quark.server.utils.patterns;

import ru.anafro.quark.server.utils.patterns.exceptions.ObjectAlreadyExistsInRegistryException;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class NamedObjectsRegistry<E> implements Iterable<E> {
    protected final ArrayList<E> registeredObjects;

    @SafeVarargs
    public NamedObjectsRegistry(E... objectsToRegister) {
        registeredObjects = new ArrayList<>();

        for(var objectToRegister : objectsToRegister) {
            add(objectToRegister);
        }
    }

    protected abstract String getNameOf(E object);

    public E get(String name) {
        for(var object : registeredObjects) {
            if(getNameOf(object).equals(name)) {
                return object;
            }
        }

        return null;
    }

    public boolean has(String name) {
        return get(name) != null;
    }

    public void add(E object) {
        if(has(getNameOf(object))) {
            throw new ObjectAlreadyExistsInRegistryException(getNameOf(object), object.getClass());
        }

        registeredObjects.add(object);
    }

    @Override
    public Iterator<E> iterator() {
        return registeredObjects.iterator();
    }

    public int count() {
        return registeredObjects.size();
    }

    public ArrayList<E> asList() {
        return registeredObjects;
    }
}
