package ru.anafro.quark.server.utils.hashing.strings;

import ru.anafro.quark.server.utils.hashing.HashingFunction;

public class SDBMHashingFunction extends HashingFunction<String> {

    public SDBMHashingFunction() {
        super("sdbm");
    }

    @Override
    public int hash(String string) {
        int hash = 0;

        for(char character : string.toCharArray()) {
            hash = character + (hash << 6) + (hash << 16) - hash;
        }

        return hash;
    }
}
