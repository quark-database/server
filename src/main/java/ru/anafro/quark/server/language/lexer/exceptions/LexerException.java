package ru.anafro.quark.server.language.lexer.exceptions;

import ru.anafro.quark.server.exceptions.QuarkException;

public class LexerException extends QuarkException {
    public LexerException(String message) {
        super(message);
    }
}
