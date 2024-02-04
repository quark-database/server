package ru.anafro.quark.server.utils.files.exceptions;

import ru.anafro.quark.server.utils.files.Directory;

public class DirectoryMoveException extends FileException {
    public DirectoryMoveException(Directory directory, String directoryName, Throwable cause) {
        super(STR."Can't move the directory \{directory.getPath()} to \{directoryName}, because of \{cause}", cause);
    }
}
