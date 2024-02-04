package ru.anafro.quark.server.utils.files.exceptions;

import ru.anafro.quark.server.utils.files.File;

public class FileWatcherResetFailureException extends FileException {
    public FileWatcherResetFailureException(File file) {
        super(STR."The watcher of the file '\{file.getPath()}' can't reset its event key.");
    }
}
