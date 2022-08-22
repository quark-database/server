package ru.anafro.quark.server.databases.data.parser.states;

import ru.anafro.quark.server.databases.data.exceptions.DatabaseFileException;
import ru.anafro.quark.server.databases.data.parser.RecordCharacterEscapeService;
import ru.anafro.quark.server.databases.data.parser.RecordParser;

public class ReadingQuotedRecordParserState extends RecordParserState {
    private boolean quoteFound = false;
    private boolean escapeMode = false;
    private final RecordCharacterEscapeService characterEscapeService = new RecordCharacterEscapeService();

    public ReadingQuotedRecordParserState(RecordParser parser) {
        super(parser);
    }

    @Override
    public void handleCharacter(char currentCharacter) { // TODO: Rewrite this, because it's too hard to read.
        if(parser.getBuffer().isEmpty() && currentCharacter != RecordParser.STRING_WRAPPER && !quoteFound) {
            throw new DatabaseFileException("The record line %s is invalid - a quoted string expected, but %s was not found".formatted(parser.getRecordLine(), RecordParser.STRING_WRAPPER));
        } else if(escapeMode) {
            parser.getBuffer().append(characterEscapeService.escaped(currentCharacter));
            escapeMode = false;
        } else if(currentCharacter == RecordParser.CHARACTER_ESCAPE) {
            escapeMode = true;
        } else if(currentCharacter == RecordParser.STRING_WRAPPER) {
            if(quoteFound) {
                parser.pushBufferContentToRecord();
                parser.switchState(new ReadingCommaRecordParserState(parser));
            } else {
                quoteFound = true;
            }
        } else {
            parser.pushCurrentCharacterToBuffer();
        }
    }

    public boolean isEscapeMode() {
        return escapeMode;
    }

    public boolean isQuoteFound() {
        return quoteFound;
    }
}
