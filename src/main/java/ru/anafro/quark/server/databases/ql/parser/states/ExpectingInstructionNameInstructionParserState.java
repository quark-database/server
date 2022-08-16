package ru.anafro.quark.server.databases.ql.parser.states;

import ru.anafro.quark.server.databases.ql.lexer.InstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.tokens.InstructionNameInstructionToken;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;

public class ExpectingInstructionNameInstructionParserState extends InstructionParserState {
    public ExpectingInstructionNameInstructionParserState(InstructionParser parser) {
        super(parser);
    }

    @Override
    public void handleToken(InstructionToken token) {
        if(token instanceof InstructionNameInstructionToken instructionNameToken) {
            parser.getLogger().debug("");
            parser.setInstructionName(instructionNameToken.getValue());
            parser.switchState(new AfterInstructionNameInstructionParserState(parser));
        } else {
            expectationError("instruction name", token.getName());
        }
    }
}
