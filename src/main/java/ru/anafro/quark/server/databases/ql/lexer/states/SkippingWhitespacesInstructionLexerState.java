package ru.anafro.quark.server.databases.ql.lexer.states;

import ru.anafro.quark.server.databases.ql.hints.InstructionHint;
import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.utils.containers.Lists;

import java.util.List;

@Deprecated
public class SkippingWhitespacesInstructionLexerState extends InstructionLexerState {
    public SkippingWhitespacesInstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        super(lexer, previousState);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        lexer.restoreState();
    }

    @Override
    public List<InstructionHint> makeHints() {
        return Lists.empty();
    }
}
