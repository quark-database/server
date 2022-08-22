package ru.anafro.quark.server.databases.data.parser.states;

import ru.anafro.quark.server.databases.data.parser.RecordParser;

public class ReadingUnquotedRecordParserState extends RecordParserState {
    public ReadingUnquotedRecordParserState(RecordParser parser) {
        super(parser);
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if(currentCharacter == RecordParser.STRING_SEPARATOR) {
            parser.switchState(new ResolvingWrapperTypeRecordParserState(parser));
            parser.pushBufferContentToRecord();
        } else {
            parser.pushCurrentCharacterToBuffer();
        }
    }
}
