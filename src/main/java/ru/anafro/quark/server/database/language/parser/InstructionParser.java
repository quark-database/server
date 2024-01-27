package ru.anafro.quark.server.database.language.parser;

import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.InstructionArguments;
import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.database.language.lexer.tokens.InstructionToken;
import ru.anafro.quark.server.database.language.parser.exceptions.InstructionParserException;
import ru.anafro.quark.server.database.language.parser.states.ExpectingInstructionNameInstructionParserState;
import ru.anafro.quark.server.database.language.parser.states.InstructionParserState;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.utils.collections.Lists;
import ru.anafro.quark.server.utils.strings.TextBuffer;
import ru.anafro.quark.server.utils.types.classes.Classes;

import java.util.ArrayList;

import static ru.anafro.quark.server.utils.objects.Nulls.byDefault;

public class InstructionParser {
    private final Logger logger = new Logger(this.getClass());
    private InstructionParserState state = new ExpectingInstructionNameInstructionParserState(this);
    private String instructionName = null;
    private InstructionArguments arguments = new InstructionArguments();
    private ArrayList<InstructionToken> tokens = Lists.empty();
    private int tokenIndex = 0;

    public synchronized Instruction parse(ArrayList<InstructionToken> tokens) {
        this.state = new ExpectingInstructionNameInstructionParserState(this);
        this.instructionName = null;
        this.arguments = new InstructionArguments();
        this.tokens = tokens;
        this.tokenIndex = 0;

        logger.debug("Got %d tokens to parse".formatted(tokens.size()));

        while (hasNextToken()) {
            logger.debug("_".repeat(50));
            for (int index = 0; index < tokens.size(); index++) {
                var token = tokens.get(index);
                logger.debug(STR."\{index == tokenIndex ? "  -> " : "    "}\{token.getName()} = \{token.getValue()}");
            }

            TextBuffer stateStackBuffer = new TextBuffer("State stack: ");

            var stateCaret = this.state;
            while (stateCaret != null) {
                stateStackBuffer.append(STR."\{Classes.toHumanReadableName(stateCaret.getClass().getSimpleName().substring(0, stateCaret.getClass().getSimpleName().length() - "InstructionParserState".length()))} -> \n");
                stateCaret = stateCaret.getPreviousState();
            }

            logger.debug(stateStackBuffer.extractContent());

            logger.debug(STR."Instruction name: \{byDefault(instructionName, "(Is not set)")}");
            logger.debug("Arguments: ");

            for (var argument : arguments) {
                logger.debug(STR."\t\{argument.name()} = (\{byDefault(argument.value(), Entity::getType, "<null type>")}) \{byDefault(argument.value(), Entity::toInstructionForm, "<null object>")}");
            }

            state.handleToken(getCurrentToken());

            logger.debug("_".repeat(50));
            logger.debug("");
            logger.debug("");
            logger.debug("");

            nextToken();
        }

        return getInstruction();
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

    public void setInstructionName(String instructionName) {
        if (!Quark.instructions().has(instructionName)) {
            throw new InstructionParserException("There is no instruction with name %s.".formatted(instructionName));
        }

        this.instructionName = instructionName;
    }

    public Instruction getInstruction() {
        if (instructionName == null) {
            throw new InstructionParserException("Instruction name is missing.");
        }

        return Quark.instructions().getOrThrow(instructionName, "There's no instruction with name %s.".formatted(instructionName));
    }

    public InstructionArguments getArguments() {
        return arguments;
    }

    public void letTheNextStateStartFromCurrentToken() {
        tokenIndex--;
    }

    public Logger getLogger() {
        return logger;
    }
}
