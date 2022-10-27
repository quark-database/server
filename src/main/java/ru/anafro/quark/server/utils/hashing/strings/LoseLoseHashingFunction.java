package ru.anafro.quark.server.utils.hashing.strings;

import ru.anafro.quark.server.utils.hashing.HashingFunction;

public class LoseLoseHashingFunction extends HashingFunction<String> {

    public LoseLoseHashingFunction() {
        super("lose-lose");
    }

    @Override
    public int hash(String string) {
        int hash = 0;

        for(char character : string.toCharArray()) {
            hash += character;
        }

        return hash;
    }
}
