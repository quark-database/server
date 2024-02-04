package ru.anafro.quark.server.utils.files.exceptions;

import ru.anafro.quark.server.utils.files.File;

public class FileDeleteException extends FileException {
    public FileDeleteException(File file, Throwable cause) {
        super(STR."Can't delete a file \{file.getPath()}, because of \{cause}", cause);
    }
}
