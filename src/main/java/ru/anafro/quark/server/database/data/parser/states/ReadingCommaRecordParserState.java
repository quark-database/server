package ru.anafro.quark.server.database.data.parser.states;

import ru.anafro.quark.server.database.data.exceptions.DatabaseFileException;
import ru.anafro.quark.server.database.data.parser.RecordParser;

public class ReadingCommaRecordParserState extends RecordParserState {
    public ReadingCommaRecordParserState(RecordParser parser) {
        super(parser);
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if(currentCharacter == RecordParser.STRING_SEPARATOR) {
            parser.switchState(new ResolvingWrapperTypeRecordParserState(parser));
        } else {
            throw new DatabaseFileException("%s expected, but %s found.".formatted(RecordParser.STRING_SEPARATOR, currentCharacter));
        }
    }
}
