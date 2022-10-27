package ru.anafro.quark.server.utils.hashing.strings;

import ru.anafro.quark.server.utils.hashing.HashingFunction;

public class DJB2HashingFunction extends HashingFunction<String> {

    public DJB2HashingFunction() {
        super("djb2");
    }

    @Override
    public int hash(String string) {
        long hash = 5381;

        for(char character : string.toCharArray()) {
            hash = ((hash << 5) + hash) + character;
        }

        return (int) hash;
    }
}
