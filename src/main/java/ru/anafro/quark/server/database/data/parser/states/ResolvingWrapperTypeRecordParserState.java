package ru.anafro.quark.server.database.data.parser.states;

import ru.anafro.quark.server.database.data.parser.RecordParser;

public class ResolvingWrapperTypeRecordParserState extends RecordParserState {
    public ResolvingWrapperTypeRecordParserState(RecordParser parser) {
        super(parser);
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        parser.letTheNextStateStartFromCurrentCharacter();

        if(currentCharacter == RecordParser.STRING_WRAPPER) {
            parser.switchState(new ReadingQuotedRecordParserState(parser));
        } else {
            parser.switchState(new ReadingUnquotedRecordParserState(parser));
        }
    }
}
