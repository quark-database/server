package ru.anafro.quark.server.files.exceptions;

import java.io.IOException;

public class CannotDeleteDirectoryException extends FileException {
    public CannotDeleteDirectoryException(String directoryName, IOException becauseOf) {
        super("A directory '%s' cannot be deleted, because of %s.".formatted(
                directoryName,
                becauseOf
        ));
        initCause(becauseOf);
    }
}
