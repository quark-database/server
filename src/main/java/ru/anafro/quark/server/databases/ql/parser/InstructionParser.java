package ru.anafro.quark.server.databases.ql.parser;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntity;
import ru.anafro.quark.server.databases.ql.lexer.tokens.InstructionToken;
import ru.anafro.quark.server.databases.ql.parser.states.ExpectingInstructionNameInstructionParserState;
import ru.anafro.quark.server.databases.ql.parser.states.InstructionParserState;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.utils.objects.Nulls;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.ArrayList;

public class InstructionParser {
    private InstructionParserState state = new ExpectingInstructionNameInstructionParserState(this);
    private String instructionName = null;
    private InstructionArguments arguments = new InstructionArguments();
    private ArrayList<InstructionToken> tokens = new ArrayList<>();
    private final Logger logger = new Logger(this.getClass());
    private int tokenIndex = 0;

    public void parse(ArrayList<InstructionToken> tokens) {
        this.state = new ExpectingInstructionNameInstructionParserState(this);
        this.instructionName = null;
        this.arguments = new InstructionArguments();
        this.tokens = tokens;
        this.tokenIndex = 0;

        logger.debug("Got %d tokens to parse".formatted(tokens.size()));

        while(hasNextToken()) {
            for(int index = 0; index < tokens.size(); index++) {
                var token = tokens.get(index);
                logger.debug((index == tokenIndex ? "  -> " : "    ") + token.getName() + " = " + token.getValue());
            }

            TextBuffer stateStackBuffer = new TextBuffer("State stack: ");

            var stateCaret = this.state;
            while(stateCaret != null) {
                stateStackBuffer.append(stateCaret.getClass().getSimpleName().substring(0, stateCaret.getClass().getSimpleName().length() - "InstructionParserState".length()) + " -> ");
                stateCaret = stateCaret.getPreviousState();
            }

            logger.debug(stateStackBuffer.extractContent());

            logger.debug("Instruction name: " + Nulls.nullOrDefault(instructionName, "<unset>"));
            logger.debug("Arguments: ");

            for(var argument : arguments) {
                logger.debug("\t" + argument.name() + " = (" + Nulls.evalOrDefault(argument.value(), InstructionEntity::getType, "<null type>") + ") " + Nulls.evalOrDefault(argument.value(), InstructionEntity::getValueAsString, "<null object>"));
            }

            logger.debug("_".repeat(50)); // TODO: change to a separate method or extract "_".repeat(..) to a constant somewhere

            state.handleToken(getCurrentToken());
            nextToken();
        }
    }

    private InstructionToken getCurrentToken() {
        return tokens.get(tokenIndex);
    }

    private boolean hasNextToken() {
        return tokenIndex < tokens.size();
    }

    private void nextToken() {
        tokenIndex++;
    }

    public void switchState(InstructionParserState state) {
        this.state = state;
    }

    public void restoreState() {
        this.state = state.getPreviousState();
    }

    public InstructionParserState getState() {
        return state;
    }

    public String getInstructionName() {
        return instructionName;
    }

    public Instruction getInstruction() {
        return Quark.instructions().get(instructionName);
    }

    public InstructionArguments getArguments() {
        return arguments;
    }

    public void setInstructionName(String instructionName) {
        this.instructionName = instructionName;
    }

    public void letTheNextStateStartFromCurrentToken() {
        tokenIndex--;
    }

    public Logger getLogger() {
        return logger;
    }
}