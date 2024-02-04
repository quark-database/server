package ru.anafro.quark.server.utils.files;

import ru.anafro.quark.server.utils.exceptions.UtilityClassInstantiationException;
import ru.anafro.quark.server.utils.files.exceptions.CannotCreateDirectoryException;
import ru.anafro.quark.server.utils.files.exceptions.FileRenameException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileSystem {
    private FileSystem() {
        throw new UtilityClassInstantiationException(getClass());
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
        for (var path : paths) {
            try {
                Files.createDirectory(Path.of(path));
            } catch (IOException exception) {
                throw new CannotCreateDirectoryException(path, exception);
            }
        }
    }

    public static void createDirectoriesIfMissing(String... paths) {
        for (var path : paths) {
            if (missing(path)) {
                createDirectories(path);
            }
        }
    }

    @SuppressWarnings("unused")
    public static void deleteIfExists(String... paths) {
        for (var stringPath : paths) {
            if (exists(stringPath)) {
                if (isFile(stringPath)) {
                    var _ = new File(stringPath).delete();
                }

                if (isDirectory(stringPath)) {
                    var directory = new File(stringPath);

                    var contents = directory.listFiles();
                    if (contents != null) {
                        for (File file : contents) {
                            if (!Files.isSymbolicLink(file.toPath())) {
                                deleteIfExists(file.getAbsolutePath());
                            }
                        }
                    }

                    var _ = directory.delete();
                }
            }
        }
    }

    public static Path rename(Path path, String newName) {
        try {
            return Files.move(path, path.resolveSibling(newName));
        } catch (IOException exception) {
            throw new FileRenameException(path, newName, exception);
        }
    }
}
