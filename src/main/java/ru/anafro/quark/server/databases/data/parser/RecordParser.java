package ru.anafro.quark.server.databases.data.parser;

import ru.anafro.quark.server.databases.data.UntypedTableRecord;
import ru.anafro.quark.server.databases.data.parser.states.RecordParserState;
import ru.anafro.quark.server.databases.data.parser.states.ResolvingWrapperTypeRecordParserState;
import ru.anafro.quark.server.utils.containers.Lists;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.ArrayList;

public class RecordParser {
    public static final char STRING_WRAPPER = '"';
    public static final char STRING_SEPARATOR = ',';
    public static final char CHARACTER_ESCAPE = '\\';
    protected RecordParserState state = new ResolvingWrapperTypeRecordParserState(this);
    protected UntypedTableRecord record = new UntypedTableRecord(new ArrayList<>());
    protected final TextBuffer buffer = new TextBuffer();
    protected String recordLine;
    protected int index = 0;

    public RecordParserState getState() {
        return state;
    }

    public void switchState(RecordParserState newState) {
        this.state = newState;
    }

    public void restoreState() {
        this.state = state.getPreviousState();
    }

    public void parse(String recordLine) {
        this.state = new ResolvingWrapperTypeRecordParserState(this);
        this.record = new UntypedTableRecord(Lists.empty());
        this.recordLine = recordLine;
        this.index = 0;

        while(index != recordLine.length()) {
            state.handleCharacter(getCurrentCharacter());
            index++;
        }

        if(buffer.containsSomething()) {
            pushBufferContentToRecord();
        }
    }

    public UntypedTableRecord getRecord() {
        return record;
    }

    public char getCurrentCharacter() {
        return recordLine.charAt(index);
    }

    public TextBuffer getBuffer() {
        return buffer;
    }

    public void pushBufferContentToRecord() {
        pushToRecord(buffer.extractContent());
    }

    public void pushToRecord(String cell) {
        record.add(cell);
    }

    public void letTheNextStateStartFromCurrentCharacter() {
        index--;
    }

    public void pushCurrentCharacterToBuffer() {
        buffer.append(getCurrentCharacter());
    }

    public String getRecordLine() {
        return recordLine;
    }
}
