package ru.anafro.quark.server.databases.ql.parser.states;

import ru.anafro.quark.server.databases.ql.lexer.InstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.tokens.ParameterNameInstructionToken;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;

public class ReadingArgumentNameInstructionParserState extends InstructionParserState {
    public ReadingArgumentNameInstructionParserState(InstructionParser parser) {
        super(parser);
    }

    @Override
    public void handleToken(InstructionToken token) {
        if(token.is("comma") || token.is("colon") || token.is("semicolon")) {
            return;
        }

        if(token instanceof ParameterNameInstructionToken parameterNameToken) {
            parser.switchState(new ReadingArgumentValueInstructionParserState(parser, this, parameterNameToken.getValue()));
        } else {
            expectationError("parameter name", token.getName());
        }
    }
}
