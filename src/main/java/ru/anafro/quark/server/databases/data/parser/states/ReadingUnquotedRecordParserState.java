package ru.anafro.quark.server.databases.data.parser.states;

import ru.anafro.quark.server.databases.data.parser.RecordParser;

public class ReadingUnquotedRecordParserState extends RecordParserState {
    public ReadingUnquotedRecordParserState(RecordParser parser) {
        super(parser);
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if(currentCharacter == RecordParser.STRING_SEPARATOR) {
            if(parser.getBuffer().isEmpty()) {
                parser.pushToRecord(null);
            } else {
                parser.pushBufferContentToRecord();
            }

            parser.switchState(new ResolvingWrapperTypeRecordParserState(parser));
        } else {
            parser.pushCurrentCharacterToBuffer();
        }
    }
}
