package ru.anafro.quark.server.console.parser.states;

import ru.anafro.quark.server.console.parser.CommandParser;

public class ReadingCommandNameCommandParserState extends CommandParserState {
    public ReadingCommandNameCommandParserState(CommandParser parser) {
        super(parser);
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if (Character.isSpaceChar(currentCharacter) && parser.getBuffer().isNotEmpty()) {
            parser.setCommandName(parser.getBuffer().extractContent());
            parser.switchState(new ReadingArgumentNameCommandParserState(parser));

            return;
        }

        parser.getBuffer().append(currentCharacter);
    }

    @Override
    public void whenParsingCompleteButBufferIsNotEmpty(String bufferContent) {
        parser.setCommandName(bufferContent);
    }
}
