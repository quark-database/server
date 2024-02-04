package ru.anafro.quark.server.utils.files.exceptions;

import ru.anafro.quark.server.utils.files.Directory;

public class DirectoryListException extends FileException {
    public DirectoryListException(Directory directory, Throwable cause) {
        super(STR."Can't get the list of files inside the directory \{directory.getPath()}, because of \{cause}", cause);
    }
}
