package ru.anafro.quark.server.utils.strings.exceptions;

import ru.anafro.quark.server.QuarkException;

public class NoMoreCharactersToTokenizeException extends QuarkException {
    public NoMoreCharactersToTokenizeException(String stringToTokenize) {
        super("No more characters can be tokenized in string: " + stringToTokenize);
    }
}
