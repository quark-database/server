package ru.anafro.quark.server.utils.files.exceptions;

import ru.anafro.quark.server.utils.files.File;

public class FileURLException extends FileException {
    public FileURLException(File file, Throwable cause) {
        super(STR."Can't get the URL from a file '\{file.getPath()}, because of \{cause}'", cause);
    }
}
