package ru.anafro.quark.server.console.parser.states;

import ru.anafro.quark.server.console.exceptions.CommandRuntimeException;
import ru.anafro.quark.server.console.exceptions.CommandSyntaxException;
import ru.anafro.quark.server.console.parser.CommandParser;

public class ReadingArgumentValueCommandParserState extends CommandParserState {
    private boolean inString = false;
    private boolean escapeMode = false;
    private final String argumentName;
    public ReadingArgumentValueCommandParserState(CommandParser parser, String argumentName) {
        super(parser);
        this.argumentName = argumentName;
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if(Character.isSpaceChar(currentCharacter) && !inString) {
            if(parser.getBuffer().isEmpty()) {
                return;
            } else {
                parser.getArguments().getArgument(argumentName).setValue(parser.getBuffer().extractContent());
                parser.switchState(new ReadingArgumentNameCommandParserState(parser));
            }
        } else if(escapeMode) {
            if(currentCharacter == '"' || currentCharacter == '\\') {
                parser.getBuffer().append(currentCharacter);
            } else {
                throw new CommandRuntimeException("There is no escaping character \\" + currentCharacter);
            }
            escapeMode = false;
        } else if(currentCharacter == '\\') {
            escapeMode = true;
        } else if(currentCharacter == '"') {
            if(parser.getBuffer().isEmpty()) {
                if(inString) {
                    parser.switchState(new ReadingArgumentNameCommandParserState(parser));
                } else {
                    inString = true;
                }
            } else if(inString) {
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
