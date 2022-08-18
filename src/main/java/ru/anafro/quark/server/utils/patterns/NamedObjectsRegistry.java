package ru.anafro.quark.server.utils.patterns;

import java.util.ArrayList;
import java.util.List;

public abstract class NamedObjectsRegistry<T> {
    protected final ArrayList<? extends T> registeredObjects;

    @SafeVarargs
    public NamedObjectsRegistry(T... objectToRegister) {
        registeredObjects = new ArrayList<T>(List.of(objectToRegister));
    }

    protected abstract String getNameOfObject(T object);

    public final T get(String name) {
        for(var object : registeredObjects) {
            if(getNameOfObject(object).equals(name)) {
                return object;
            }
        }

        return null;
    }

    public boolean has(String name) {
        return get(name) != null;
    }

    public void add(T object) {
        if(has(getNameOfObject(object))) {
            throw new ObjectAlreadyExistsInRegistryException(); // TODO: Start from here
        }
    }
}
