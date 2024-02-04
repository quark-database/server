package ru.anafro.quark.server.utils.files.exceptions;

import ru.anafro.quark.server.utils.files.File;

public class FileReadException extends FileException {
    public FileReadException(File file, Throwable cause) {
        super(STR."Can't read a file '\{file.getPath()}', because of \{cause}", cause);
    }
}
