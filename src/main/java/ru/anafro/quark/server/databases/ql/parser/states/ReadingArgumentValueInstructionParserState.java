package ru.anafro.quark.server.databases.ql.parser.states;

import ru.anafro.quark.server.databases.ql.InstructionArgument;
import ru.anafro.quark.server.databases.ql.lexer.InstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.LiteralInstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;

public class ReadingArgumentValueInstructionParserState extends InstructionParserState {
    private final String argumentName;

    public ReadingArgumentValueInstructionParserState(InstructionParser parser, InstructionParserState previousState, String argumentName) {
        super(parser, previousState);
        this.argumentName = argumentName;
    }

    public String getArgumentName() {
        return argumentName;
    }

    @Override
    public void handleToken(InstructionToken token) {
        if(token.is("equals sign")) {
            return;
        }

        if(token instanceof LiteralInstructionToken literalToken) {
            parser.getArguments().add(new InstructionArgument(argumentName, literalToken.toEntity()));
            parser.restoreState();
        } else if(token instanceof ConstructorNameInstructionToken constructorNameToken) {
            parser.switchState(new ReadingConstructorArgumentsAsInstructionArgumentInstructionParserState(parser, getPreviousState(), constructorNameToken.getConstructor(), parser.getInstruction(), argumentName));
        } else {
            parser.letTheNextStateStartFromCurrentToken();
            parser.restoreState();
        }
    }
}
