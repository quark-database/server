package ru.anafro.quark.server.utils.files.exceptions;

import ru.anafro.quark.server.utils.files.File;

public class FileWatcherInterruptionException extends FileException {
    public FileWatcherInterruptionException(File file, Throwable cause) {
        super(STR."The watcher of the file \{file.getPath()} is interrupted.", cause);
    }
}
