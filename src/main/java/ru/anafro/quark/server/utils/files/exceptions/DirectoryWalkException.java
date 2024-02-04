package ru.anafro.quark.server.utils.files.exceptions;

import ru.anafro.quark.server.utils.files.Directory;

public class DirectoryWalkException extends FileException {
    public DirectoryWalkException(Directory directory, Throwable cause) {
        super(STR."Can't walk files inside the directory \{directory.getPath()}, because of \{cause}", cause);
    }
}
