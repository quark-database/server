package ru.anafro.quark.server.utils.files.exceptions;

import ru.anafro.quark.server.utils.files.File;

public class FileNotFoundException extends FileException {
    public FileNotFoundException(File file) {
        super(STR."The file '\{file.getPath()}' does not exist.");
    }
}
