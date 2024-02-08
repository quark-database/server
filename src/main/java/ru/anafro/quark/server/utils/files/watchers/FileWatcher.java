package ru.anafro.quark.server.utils.files.watchers;

import ru.anafro.quark.server.multithreading.Service;
import ru.anafro.quark.server.utils.files.File;
import ru.anafro.quark.server.utils.files.exceptions.FileCreateWatcherException;
import ru.anafro.quark.server.utils.files.exceptions.FileWatcherInterruptionException;
import ru.anafro.quark.server.utils.files.exceptions.FileWatcherResetFailureException;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;

public class FileWatcher extends Service {
    private final File file;
    private final Runnable runnable;
    private volatile boolean isRunning;

    public FileWatcher(File file, Runnable runnable) {
        this.file = file;
        this.runnable = runnable;
    }

    @Override
    public void start() {
        try {
            var watcher = FileSystems.getDefault().newWatchService();
            file.getPath().getParent().register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);

            while (this.isRunning) {
                WatchKey key;

                try {
                    while (((key = watcher.take()) != null)) {
                        for (var event : key.pollEvents()) {
                            if (((Path) event.context()).toString().endsWith(file.getName())) {
                                runnable.run();
                            }
                        }

                        if (!key.reset()) {
                            throw new FileWatcherResetFailureException(file);
                        }
                    }
                } catch (InterruptedException exception) {
                    throw new FileWatcherInterruptionException(file, exception);
                }
            }
        } catch (IOException exception) {
            throw new FileCreateWatcherException(file, exception);
        }
    }

    @Override
    public void stop() {
        this.isRunning = false;
    }
}
