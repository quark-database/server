package ru.anafro.quark.server.utils.hashing;

import ru.anafro.quark.server.utils.patterns.NamedObjectsList;

public class AbstractHashingFunctionList<T> extends NamedObjectsList<HashingFunction<T>> {

    @Override
    protected String getNameOf(HashingFunction<T> function) {
        return function.getName();
    }
}
