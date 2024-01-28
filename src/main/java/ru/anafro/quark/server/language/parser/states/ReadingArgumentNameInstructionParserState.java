package ru.anafro.quark.server.language.parser.states;

import ru.anafro.quark.server.language.lexer.tokens.InstructionToken;
import ru.anafro.quark.server.language.lexer.tokens.ParameterNameInstructionToken;
import ru.anafro.quark.server.language.parser.InstructionParser;

public class ReadingArgumentNameInstructionParserState extends InstructionParserState {
    public ReadingArgumentNameInstructionParserState(InstructionParser parser) {
        super(parser);
    }

    @Override
    public void handleToken(InstructionToken token) {
        if (token.is("comma") || token.is("colon") || token.is("semicolon")) {
            logger.debug(STR."Ignoring \{token.getName()}");
            return;
        }

        if (token instanceof ParameterNameInstructionToken parameterNameToken) {
            logger.debug("Found an argument name. Expecting an argument value next time");
            parser.switchState(new ReadingArgumentValueInstructionParserState(parser, this, parameterNameToken.getValue()));
        } else {
            throwExpectationError("parameter name", token.getName());
        }
    }
}
