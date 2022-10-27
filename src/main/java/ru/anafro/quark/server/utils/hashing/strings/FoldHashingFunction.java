package ru.anafro.quark.server.utils.hashing.strings;

import ru.anafro.quark.server.utils.hashing.HashingFunction;

public class FoldHashingFunction extends HashingFunction<String> {
    public FoldHashingFunction() {
        super("fold");
    }

    @Override
    public int hash(String string) {
        int chunkLength = string.length() / 4;
        long hash = 0;

        for (int index = 0; index < chunkLength; index++) {
            char[] chunk = string.substring(index * 4, (index * 4) + 4).toCharArray();
            long multiplier = 1;

            for(char character : chunk) {
                hash += character * multiplier;
                multiplier *= 256;
            }
        }

        char[] chunk = string.substring(chunkLength * 4).toCharArray();
        long multiplier = 1;

        for(char character : chunk) {
            hash += character * multiplier;
            multiplier *= 256;
        }

        return (int) Math.abs(hash);
    }
}
