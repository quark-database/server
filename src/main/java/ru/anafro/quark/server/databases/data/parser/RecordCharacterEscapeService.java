package ru.anafro.quark.server.databases.data.parser;

import ru.anafro.quark.server.databases.data.exceptions.DatabaseFileException;

public class RecordCharacterEscapeService {
    public char escaped(char character) {
        return switch(character) {
            case 'n' -> '\n';
            case 't' -> '\t';
            case '\\' -> '\\';
            default -> throw new DatabaseFileException("Cannot escape character %s.".formatted(character)); // TODO: Create a new exception type
        };
    }
}
