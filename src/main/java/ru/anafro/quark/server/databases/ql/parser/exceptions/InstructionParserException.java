package ru.anafro.quark.server.databases.ql.parser.exceptions;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;

public class InstructionParserException extends DatabaseException {
    public InstructionParserException(String message) {
        super(message);
    }
}
