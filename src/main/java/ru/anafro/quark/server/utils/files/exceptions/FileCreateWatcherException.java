package ru.anafro.quark.server.utils.files.exceptions;

import ru.anafro.quark.server.utils.files.File;

public class FileCreateWatcherException extends FileException {
    public FileCreateWatcherException(File file, Throwable cause) {
        super(STR."Can't create a watcher for file \{file.getPath()}, because of \{cause}", cause);
    }
}
