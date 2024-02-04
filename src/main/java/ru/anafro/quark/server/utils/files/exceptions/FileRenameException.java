package ru.anafro.quark.server.utils.files.exceptions;

import java.nio.file.Path;

public class FileRenameException extends FileException {
    public FileRenameException(Path path, String newName, Throwable cause) {
        super(STR."Can't rename the file \{path} to \{newName}, because of \{cause}", cause);
    }
}
