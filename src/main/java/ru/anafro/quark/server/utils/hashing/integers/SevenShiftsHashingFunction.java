package ru.anafro.quark.server.utils.hashing.integers;

import ru.anafro.quark.server.utils.hashing.HashingFunction;

public class SevenShiftsHashingFunction extends HashingFunction<Integer> {
    public SevenShiftsHashingFunction() {
        super("7-shifts");
    }

    @Override
    public int hash(Integer integer) {
        int hash = integer;

        hash -= (hash << 6);
        hash ^= (hash >>> 17);
        hash -= (hash << 9);
        hash ^= (hash << 4);
        hash -= (hash << 3);
        hash ^= (hash << 10);
        hash ^= (hash >> 15);

        return hash;
    }
}
