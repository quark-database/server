package ru.anafro.quark.server.databases.ql.parser.states;

import ru.anafro.quark.server.databases.ql.lexer.InstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.LiteralInstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.tokens.ColonInstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;

public class AfterInstructionNameInstructionParserState extends InstructionParserState {
    public AfterInstructionNameInstructionParserState(InstructionParser parser) {
        super(parser);
    }

    @Override
    public void handleToken(InstructionToken token) {
        if(token instanceof ColonInstructionToken colonToken) {
            if(parser.getInstruction().getParameters().hasGeneralParameter()) {
                expectationError("object", colonToken.getName());
            }

            parser.switchState(new ReadingArgumentNameInstructionParserState(parser));
        } else if(!parser.getInstruction().getParameters().hasGeneralParameter()) {
            expectationError("colon", token.getName());
        } else if(token instanceof LiteralInstructionToken || token instanceof ConstructorNameInstructionToken) {
            parser.letTheNextStateStartFromCurrentToken();
            parser.switchState(new ReadingArgumentValueInstructionParserState(parser, new ReadingArgumentNameInstructionParserState(parser), parser.getInstruction().getParameters().getGeneralParameter().getName()));
        } else {
            expectationError("colon or object", token.getName());
        }
    }
}
