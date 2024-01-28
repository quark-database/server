package ru.anafro.quark.server.language.lexer.exceptions;

public class LexingAlreadyPerformedException extends LexerException {
    public LexingAlreadyPerformedException() {
        super("lexer.performLexing() was invoked twice");
    }
}
