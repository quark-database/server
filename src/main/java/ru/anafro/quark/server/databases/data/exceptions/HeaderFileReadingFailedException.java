package ru.anafro.quark.server.databases.data.exceptions;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class HeaderFileReadingFailedException extends DatabaseFileException {
    public HeaderFileReadingFailedException(String headerFileName, Throwable cause) {
        super("The header file %s cannot be read because of %s.".formatted(quoted(headerFileName), cause));
        initCause(cause);
    }
}