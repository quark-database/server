package ru.anafro.quark.server.utils.files.exceptions;

public class CannotCreateDirectoryException extends FileException {
    public CannotCreateDirectoryException(String directoryName, Throwable becauseOf) {
        super("Cannot create a directory at '%s', because of %s.".formatted(
                directoryName,
                becauseOf
        ));
        initCause(becauseOf);
    }
}
