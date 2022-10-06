package ru.anafro.quark.server.utils.hashing.strings;

import ru.anafro.quark.server.utils.hashing.HashingFunction;

public class DefaultStringHashingFunction extends HashingFunction<String> {
    public DefaultStringHashingFunction() {
        super("default");
    }

    @Override
    public int hash(String string) {
        return string.hashCode();
    }
}
