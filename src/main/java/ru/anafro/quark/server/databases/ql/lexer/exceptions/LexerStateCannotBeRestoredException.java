package ru.anafro.quark.server.databases.ql.lexer.exceptions;

import ru.anafro.quark.server.databases.ql.lexer.states.InstructionLexerState;

public class LexerStateCannotBeRestoredException extends LexerException {
    public LexerStateCannotBeRestoredException(InstructionLexerState lastState) {
        super("lexer.restoreState() was called on " + lastState.getClass().getSimpleName() + ", which is current lexer state, but this state doesn't have a state to restore to. (Previous state is null. Did you forget to initialise the previous state of this state?)");
    }
}
