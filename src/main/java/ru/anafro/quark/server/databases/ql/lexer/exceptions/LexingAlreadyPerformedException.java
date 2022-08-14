package ru.anafro.quark.server.databases.ql.lexer.exceptions;

public class LexingAlreadyPerformedException extends LexerException {
    public LexingAlreadyPerformedException() {
        super("lexer.performLexing() was invoked twice");
    }
}
