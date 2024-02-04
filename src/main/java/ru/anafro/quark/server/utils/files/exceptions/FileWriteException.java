package ru.anafro.quark.server.utils.files.exceptions;

import ru.anafro.quark.server.utils.files.File;

public class FileWriteException extends FileException {
    public FileWriteException(File file, Throwable cause) {
        super(STR."Can't write to a file '\{file.getPath()}', because of \{cause}", cause);
    }
}
