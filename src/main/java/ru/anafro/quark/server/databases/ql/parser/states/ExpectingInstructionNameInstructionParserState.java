package ru.anafro.quark.server.databases.ql.parser.states;

import ru.anafro.quark.server.databases.ql.lexer.tokens.InstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.tokens.InstructionNameInstructionToken;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;

public class ExpectingInstructionNameInstructionParserState extends InstructionParserState {
    public ExpectingInstructionNameInstructionParserState(InstructionParser parser) {
        super(parser);
    }

    @Override
    public void handleToken(InstructionToken token) {
        if(token instanceof InstructionNameInstructionToken instructionNameToken) {
            logger.debug("Found an instruction name token. Setting an instruction name and switching state");
            parser.setInstructionName(instructionNameToken.getValue());
            parser.switchState(new AfterInstructionNameInstructionParserState(parser));
        } else {
            throwExecutionError("instruction name", token.getName());
        }
    }
}
