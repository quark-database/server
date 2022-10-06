package ru.anafro.quark.server.utils.hashing.integers;

import ru.anafro.quark.server.utils.hashing.HashingFunction;

public class DefaultIntegerHashingFunction extends HashingFunction<Integer> {
    public DefaultIntegerHashingFunction() {
        super("default");
    }

    @Override
    public int hash(Integer integer) {
        return integer.hashCode();
    }
}
