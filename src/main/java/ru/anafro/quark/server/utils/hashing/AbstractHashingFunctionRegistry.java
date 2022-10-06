package ru.anafro.quark.server.utils.hashing;

import ru.anafro.quark.server.utils.patterns.NamedObjectsRegistry;

public class AbstractHashingFunctionRegistry<T> extends NamedObjectsRegistry<HashingFunction<T>> {

    @Override
    protected String getNameOf(HashingFunction<T> function) {
        return function.getName();
    }
}
