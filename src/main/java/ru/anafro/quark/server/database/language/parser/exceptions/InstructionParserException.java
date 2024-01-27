package ru.anafro.quark.server.database.language.parser.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class InstructionParserException extends DatabaseException {
    public InstructionParserException(String message) {
        super(message);
    }
}
