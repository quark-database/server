package ru.anafro.quark.server.databases.data.parser;

import ru.anafro.quark.server.databases.data.exceptions.DatabaseFileException;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.List;

public class RecordCharacterEscapeService {
    public record EscapableCharacter(char escapableCharacter, char actualCharacter) {
        //
    }

    private static final List<EscapableCharacter> ESCAPABLE_CHARACTERS = List.of(
            new EscapableCharacter('n', '\n'),
            new EscapableCharacter('t', '\t'),
            new EscapableCharacter('\\', '\\'),
            new EscapableCharacter('\"', '\"')
    );

    public boolean isEscapable(char character) {
        for(var escapableCharacter : ESCAPABLE_CHARACTERS) {
            if(character == escapableCharacter.escapableCharacter()) {
                return true;
            }
        }

        return false;
    }

    public char escaped(char character) {
        if(isEscapable(character)) {
            return ESCAPABLE_CHARACTERS.stream().filter(escapable -> escapable.escapableCharacter == character).findFirst().get().actualCharacter();
        }

        throw new DatabaseFileException("Cannot escape character %s.".formatted(character)); // TODO: Create a new exception type
    }

    public boolean isWrappable(char character) {
        for(var escapableCharacter : ESCAPABLE_CHARACTERS) {
            if(character == escapableCharacter.actualCharacter()) {
                return true;
            }
        }

        return false;
    }

    public char wrapped(char character) {
        if(isWrappable(character)) {
            return ESCAPABLE_CHARACTERS.stream().filter(escapable -> escapable.actualCharacter == character).findFirst().get().escapableCharacter();
        }

        throw new DatabaseFileException("Cannot wrap character %s.".formatted(character)); // TODO: Create a new exception type
    }

    public String wrapEscapableCharacters(String text) {
        TextBuffer wrappedText = new TextBuffer();

        for(var character : text.toCharArray()) {
            if(isWrappable(character)) {
                wrappedText.append('\\');
                wrappedText.append(wrapped(character));
            } else {
                wrappedText.append(character);
            }
        }

        return wrappedText.extractContent();
    }
}
