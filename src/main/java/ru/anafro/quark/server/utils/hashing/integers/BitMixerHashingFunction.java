package ru.anafro.quark.server.utils.hashing.integers;

import ru.anafro.quark.server.utils.hashing.HashingFunction;

public class BitMixerHashingFunction extends HashingFunction<Integer> {
    public BitMixerHashingFunction() {
        super("bit-mixer");
    }

    @Override
    public int hash(Integer integer) {
        int hash = integer;

        hash = ((hash >>> 16) ^ hash) * 0x45d9f3b;
        hash = ((hash >>> 16) ^ hash) * 0x45d9f3b;
        hash = ((hash >>> 16) ^ hash);

        return hash;
    }
}
