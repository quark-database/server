package ru.anafro.quark.server.utils.files.exceptions;

import ru.anafro.quark.server.utils.files.File;

public class FileCreateException extends FileException {
    public FileCreateException(File file, Throwable cause) {
        super(STR."Can't create a file '\{file.getPath()}', because of \{cause}", cause);
    }
}
