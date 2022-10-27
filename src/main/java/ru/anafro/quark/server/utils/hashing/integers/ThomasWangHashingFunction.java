package ru.anafro.quark.server.utils.hashing.integers;

import ru.anafro.quark.server.utils.hashing.HashingFunction;

public class ThomasWangHashingFunction extends HashingFunction<Integer> {
    public ThomasWangHashingFunction() {
        super("thomas-wang");
    }

    @Override
    public int hash(Integer integer) {
        int hash = integer;

        hash = (hash + 0x7ed55d16) + (hash << 12);
        hash = (hash ^ 0xc761c23c) ^ (hash >>> 19);
        hash = (hash + 0x165667b1) + (hash << 5);
        hash = (hash + 0xd3a2646c) ^ (hash << 9);
        hash = (hash + 0xfd7046c5) + (hash << 3);
        hash = (hash ^ 0xb55a4f09) ^ (hash >>> 16);

        return hash;
    }
}
