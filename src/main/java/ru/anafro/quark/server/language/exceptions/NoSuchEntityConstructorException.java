package ru.anafro.quark.server.language.exceptions;

import ru.anafro.quark.server.language.lexer.exceptions.LexerException;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class NoSuchEntityConstructorException extends LexerException {
    public NoSuchEntityConstructorException(String constructorName) {
        super("No such constructor named %s.".formatted(quoted(constructorName)));
    }
}
