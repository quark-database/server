package ru.anafro.quark.server.console.parser.states;

import ru.anafro.quark.server.console.parser.CommandParser;

public class ReadingArgumentNameCommandParserState extends CommandParserState {
    public ReadingArgumentNameCommandParserState(CommandParser parser) {
        super(parser);
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if (Character.isSpaceChar(currentCharacter) && parser.getBuffer().isNotEmpty()) {
            parser.getArguments().addEmpty(parser.getBufferContent());
            parser.switchState(new ReadingArgumentValueCommandParserState(parser, parser.getBuffer().extractContent()));

            return;
        }

        parser.getBuffer().append(currentCharacter);
    }

    @Override
    public void whenParsingCompleteButBufferIsNotEmpty(String bufferContent) {
        parser.getArguments().addEmpty(parser.getBufferContent());
    }
}
