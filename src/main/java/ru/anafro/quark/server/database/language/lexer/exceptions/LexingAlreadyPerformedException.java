package ru.anafro.quark.server.database.language.lexer.exceptions;

public class LexingAlreadyPerformedException extends LexerException {
    public LexingAlreadyPerformedException() {
        super("lexer.performLexing() was invoked twice");
    }
}
