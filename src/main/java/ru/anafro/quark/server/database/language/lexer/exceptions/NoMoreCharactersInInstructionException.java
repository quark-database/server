package ru.anafro.quark.server.database.language.lexer.exceptions;

public class NoMoreCharactersInInstructionException extends LexerException {
    public NoMoreCharactersInInstructionException() {
        super("Invocation of lexer.nextCharacter() is unexpected - there are no more character to lex.");
    }
}
