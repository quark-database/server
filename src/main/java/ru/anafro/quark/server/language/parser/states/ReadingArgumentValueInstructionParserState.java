package ru.anafro.quark.server.language.parser.states;

import ru.anafro.quark.server.language.InstructionArgument;
import ru.anafro.quark.server.language.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.language.lexer.tokens.InstructionToken;
import ru.anafro.quark.server.language.lexer.tokens.LiteralInstructionToken;
import ru.anafro.quark.server.language.parser.InstructionParser;

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
        if (token.is("equals sign")) {
            logger.debug(STR."Ignoring \{token.getName()}"); // TODO: Add English.appendArticle
            return;
        }

        if (token instanceof LiteralInstructionToken literalToken) {
            logger.debug("Found a literal. Assigning it to an argument");
            parser.getArguments().add(new InstructionArgument(argumentName, literalToken.toEntity()));
            parser.restoreState();
        } else if (token instanceof ConstructorNameInstructionToken constructorNameToken) {
            logger.debug("Found an instruction name. Starting reading its parameters");
            parser.switchState(new ReadingConstructorArgumentsAsInstructionArgumentInstructionParserState(parser, getPreviousState(), constructorNameToken.getConstructor(), parser.getInstruction(), argumentName));
        } else {
            logger.debug(STR."Found \{token.getName()}, so restoring the state and let the restored state deal with it");
            parser.letTheNextStateStartFromCurrentToken();
            parser.restoreState();
        }
    }
}
