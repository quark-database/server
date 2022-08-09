package ru.anafro.quark.server.console.parser.states;

import ru.anafro.quark.server.console.parser.CommandParser;

public class ReadingCommandNameCommandParserState extends CommandParserState {
    public ReadingCommandNameCommandParserState(CommandParser parser) {
        super(parser);
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if(Character.isSpaceChar(currentCharacter)) {
            if(parser.getBuffer().isEmpty()) {
                return;
            } else {
                parser.setCommandName(parser.getBuffer().extractValue());
                parser.switchState(new ReadingArgumentNameCommandParserState(parser));
            }
        } else {
            parser.getBuffer().append(currentCharacter);
        }
    }

    @Override
    public void whenParsingCompleteButBufferIsNotEmpty(String bufferContent) {
        parser.setCommandName(bufferContent);
    }
}
