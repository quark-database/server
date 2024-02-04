package ru.anafro.quark.server.utils.files.exceptions;

import ru.anafro.quark.server.utils.files.Directory;

import java.nio.file.Path;

public class DirectoryCopyException extends FileException {
    public DirectoryCopyException(Directory directory, Path source, Path destination, Throwable cause) {
        super(STR."Can't copy a file '\{source}' from the directory '\{directory.getPath()}' to '\{destination}', because of \{cause}", cause);
    }
}
