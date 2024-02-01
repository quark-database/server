package ru.anafro.quark.server.language.exceptions;

import ru.anafro.quark.server.language.lexer.exceptions.LexerException;

public class NoSuchEntityConstructorException extends LexerException {
    public NoSuchEntityConstructorException(String constructorName) {
        super(STR."No such constructor named '\{constructorName}'.");
    }
}
