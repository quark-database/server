package ru.anafro.quark.server.files;

import ru.anafro.quark.server.files.exceptions.CannotCreateDirectoryException;
import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileSystem {
    private FileSystem() {
        throw new CallingUtilityConstructorException(getClass());
    }

    public static boolean isFile(String path) {
        return new File(path).isFile();
    }

    public static boolean isDirectory(String path) {
        return new File(path).isDirectory();
    }

    public static boolean exists(String path) {
        var directory = new File(path);

        return directory.exists();
    }

    public static boolean missing(String path) {
        return !exists(path);
    }

    public static void createDirectories(String... paths) {
        for(var path : paths) {
            try {
                Files.createDirectory(Path.of(path));
            } catch (IOException exception) {
                throw new CannotCreateDirectoryException(path, exception);
            }
        }
    }

    public static void createDirectoriesIfMissing(String... paths) {
        for(var path : paths) {
            if (missing(path)) {
                createDirectories(path);
            }
        }
    }

    public static void deleteIfExists(String... paths) {
        for (var stringPath : paths) {
            if(isFile(stringPath) && exists(stringPath)) {
                new File(stringPath).delete();
            }

            if(isDirectory(stringPath) && exists(stringPath)) {
                var path = Path.of(stringPath);

                var directory = new File(path.toUri());
                var content = directory.listFiles();

                if(content != null) {
                    for (var fileInside : content) {
                        deleteIfExists(fileInside.getAbsolutePath());
                    }
                }

                directory.delete();
            }
        }
    }
}
