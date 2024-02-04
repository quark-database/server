package ru.anafro.quark.server.utils.files.exceptions;

import ru.anafro.quark.server.utils.files.File;

public class FileCopyException extends FileException {
    public FileCopyException(File file, Throwable cause) {
        super(STR."Can't copy the file '\{file.getPath()}', because of \{cause}", cause);
    }
}
