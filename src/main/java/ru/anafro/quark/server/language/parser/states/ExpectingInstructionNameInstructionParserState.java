package ru.anafro.quark.server.language.parser.states;

import ru.anafro.quark.server.language.lexer.tokens.InstructionNameInstructionToken;
import ru.anafro.quark.server.language.lexer.tokens.InstructionToken;
import ru.anafro.quark.server.language.parser.InstructionParser;

public class ExpectingInstructionNameInstructionParserState extends InstructionParserState {
    public ExpectingInstructionNameInstructionParserState(InstructionParser parser) {
        super(parser);
    }

    @Override
    public void handleToken(InstructionToken token) {
        if (token instanceof InstructionNameInstructionToken instructionNameToken) {
            logger.debug("Found an instruction name token. Setting an instruction name and switching state");
            parser.setInstructionName(instructionNameToken.getValue());
            parser.switchState(new AfterInstructionNameInstructionParserState(parser));
        } else {
            throwExpectationError("instruction name", token.getName());
        }
    }
}
