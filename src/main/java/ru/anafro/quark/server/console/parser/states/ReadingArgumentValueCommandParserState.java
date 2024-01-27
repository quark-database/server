package ru.anafro.quark.server.console.parser.states;

import ru.anafro.quark.server.console.exceptions.CommandRuntimeException;
import ru.anafro.quark.server.console.exceptions.CommandSyntaxException;
import ru.anafro.quark.server.console.parser.CommandParser;

public class ReadingArgumentValueCommandParserState extends CommandParserState {
    private final String argumentName;
    private boolean inString = false;
    private boolean escapeMode = false;

    public ReadingArgumentValueCommandParserState(CommandParser parser, String argumentName) {
        super(parser);
        this.argumentName = argumentName;
    }

    /**
     * If the character is a space, and we're not in a string, then we're done reading the argument
     * value, so we switch to the ReadingArgumentNameCommandParserState. If the character is a
     * backslash, then we're in escape mode. If the character is a quotation mark, then we're either
     * starting or ending a string. If the character is anything else, then we add it to the buffer
     *
     * @param currentCharacter The current character that is being read.
     */
    @Override
    public void handleCharacter(char currentCharacter) {
        if (Character.isSpaceChar(currentCharacter) && !inString) {
            if (parser.getBuffer().isNotEmpty()) {
                parser.getArguments().getArgument(argumentName).setValue(parser.getBuffer().extractContent());
                parser.switchState(new ReadingArgumentNameCommandParserState(parser));
            }
        } else if (escapeMode) {
            if (currentCharacter == '\'' || currentCharacter == '\\') {
                parser.getBuffer().append(currentCharacter);
            } else {
                throw new CommandRuntimeException(STR."There is no escaping character \\\{currentCharacter}");
            }
            escapeMode = false;
        } else if (currentCharacter == '\\') {
            escapeMode = true;
        } else if (currentCharacter == '\'') {
            if (parser.getBuffer().isEmpty()) {
                if (inString) {
                    parser.switchState(new ReadingArgumentNameCommandParserState(parser));
                } else {
                    inString = true;
                }
            } else if (inString) {
                parser.getArguments().getArgument(argumentName).setValue(parser.getBuffer().extractContent());
                parser.switchState(new ReadingArgumentNameCommandParserState(parser));
            } else {
                throw new CommandSyntaxException("You tried to start a string, but you've written something before it.", parser.getCommandString(), parser.getIndex());
            }
        } else {
            parser.getBuffer().append(currentCharacter);
        }
    }

    @Override
    public void whenParsingCompleteButBufferIsNotEmpty(String bufferContent) {
        parser.getArguments().getArgument(argumentName).setValue(parser.getBuffer().extractContent());
    }
}
