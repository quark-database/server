package ru.anafro.quark.server.databases.ql.parser.states;

import ru.anafro.quark.server.databases.ql.lexer.tokens.*;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;

public class AfterInstructionNameInstructionParserState extends InstructionParserState {
    public AfterInstructionNameInstructionParserState(InstructionParser parser) {
        super(parser);
    }

    @Override
    public void handleToken(InstructionToken token) {
        if(token.is("semicolon") && !parser.getInstruction().getParameters().hasGeneralParameter()) {
            return;
        }

        if(token instanceof ColonInstructionToken colonToken) {
            if(parser.getInstruction().getParameters().hasGeneralParameter()) {
                throwExcectationError("object", colonToken.getName());
            }

            logger.debug("Found a colon, starting reading arguments");
            parser.switchState(new ReadingArgumentNameInstructionParserState(parser));
        } else if(!parser.getInstruction().getParameters().hasGeneralParameter()) {
            throwExcectationError("colon or semicolon", token.getName());
        } else if(token instanceof LiteralInstructionToken || token instanceof ConstructorNameInstructionToken) {
            logger.debug("Found an object. It means that it's a general parameter. Let the 'reading argument value' deal with it");
            parser.letTheNextStateStartFromCurrentToken();
            parser.switchState(new ReadingArgumentValueInstructionParserState(parser, new ReadingArgumentNameInstructionParserState(parser), parser.getInstruction().getParameters().getGeneralParameter().getName()));
        } else {
            throwExcectationError("colon or object", token.getName());
        }
    }
}
