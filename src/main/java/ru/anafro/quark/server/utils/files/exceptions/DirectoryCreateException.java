package ru.anafro.quark.server.utils.files.exceptions;

public class DirectoryCreateException extends FileException {
    public DirectoryCreateException(String directoryName, Throwable cause) {
        super(STR."Can't create a directory '\{directoryName}', because of \{cause}", cause);
    }
}
