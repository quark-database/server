package ru.anafro.quark.server.utils.strings;

import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.utils.arrays.Arrays;

import java.util.Objects;

public final class English {
    private English() {

    }

    public static String articleFor(String noun) {
        if(Objects.isNull(noun)) {
            throw new QuarkException("Cannot resolve an article for a null string.");
        }

        if(noun.isBlank()) {
            throw new QuarkException("Cannot resolve an article for a blank string. Please, provide a noun, like %s, %s or %s".formatted("dog", "apple", "stick"));
        }

        if(Arrays.contains(new Character[] {'a', 'e', 'i', 'o', 'u'}, Character.toLowerCase(noun.charAt(0)))) {
            return "an";
        } else {
            return "a";
        }
    }
}
