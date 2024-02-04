package ru.anafro.quark.server.utils.files;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.utils.files.exceptions.*;
import ru.anafro.quark.server.utils.files.filters.FileFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static ru.anafro.quark.server.utils.arrays.Arrays.allMatch;

public class Directory implements Iterable<File> {
    private final FileFilter[] filters;
    private Path path;

    public Directory(Path path, FileFilter... filters) {
        this.path = path;
        this.filters = filters;
    }

    public Directory(String relativePath, FileFilter... filters) {
        this(Path.of(relativePath), filters);
    }

    @NotNull
    @Override
    public final Iterator<File> iterator() {
        return files().iterator();
    }

    public boolean hasDirectory(String... filePath) {
        return new Directory(getFilePath(filePath)).exists();
    }

    public File getFile(String... filePath) {
        return new File(getFilePath(filePath));
    }

    public Path getFilePath(String... filePath) {
        return Paths.get(path.toAbsolutePath().toString(), filePath);
    }

    public String getAbsoluteFilePath(String... filePath) {
        return this.getFilePath(filePath).toString();
    }

    public Directory getDirectory(String... filePath) {
        return new Directory(Path.of(path.toAbsolutePath().toString(), filePath));
    }

    public Path getPath() {
        return path;
    }

    public Directory getParent() {
        return new Directory(path.getParent());
    }

    public File createFile(String fileName) {
        var file = new File(path.resolve(fileName));
        file.create();

        return file;
    }

    public void createFile(String fileName, String content) {
        var file = createFile(fileName);
        file.write(content);
    }

    public Directory createDirectory(String directoryName) {
        try {
            return new Directory(Files.createDirectories(getFilePath(directoryName)));
        } catch (IOException exception) {
            throw new DirectoryCreateException(directoryName, exception);
        }
    }

    public boolean hasDirectory(String directoryName) {
        var directoryPath = getFilePath(directoryName);

        return Files.exists(directoryPath) && Files.isDirectory(directoryPath);
    }

    public List<Directory> getDirectories() {
        try (var paths = Files.list(path)) {
            return paths.filter(path -> path.toFile().isDirectory()).map(Directory::new).toList();
        } catch (IOException exception) {
            throw new DirectoryListException(this, exception);
        }
    }

    public Directory getSibling(String siblingName) {
        return new Directory(path.resolveSibling(siblingName));
    }

    public void copy(Path destinationPath) {
        destinationPath.toFile().mkdirs();

        paths()
                .forEach(source -> {
                    if (path.getNameCount() == source.getNameCount()) {
                        return;
                    }

                    var destination = destinationPath.resolve(source.subpath(path.getNameCount(), source.getNameCount()));

                    try {
                        var sourceFile = source.toFile();
                        var destinationFile = destination.toFile();
                        if (sourceFile.isDirectory()) {
                            destinationFile.getParentFile().mkdirs();
                        } else {
                            destinationFile.getParentFile().mkdirs();
                        }

                        Files.copy(source, destination);
                    } catch (IOException exception) {
                        throw new DirectoryCopyException(this, source, destination, exception);
                    }
                });
    }

    public void moveTo(String directoryName) {
        try {
            Files.move(path, getParent().getFilePath(directoryName));
        } catch (IOException exception) {
            throw new DirectoryMoveException(this, directoryName, exception);
        }
    }

    public void delete() {
        if (doesntExist()) {
            return;
        }

        files().sorted(Comparator.reverseOrder()).forEach(File::delete);
    }

    private Stream<Path> paths() {
        try (var walk = Files.walk(path)) {
            return walk.filter(path -> this.passThroughFilters(new File(path))).toList().stream();
        } catch (IOException exception) {
            throw new DirectoryWalkException(this, exception);
        }
    }

    public Stream<File> files() {
        return paths().map(File::new);
    }

    public String getName() {
        return path.getFileName().toString();
    }

    public boolean exists() {
        return Files.exists(path) && Files.isDirectory(path);
    }

    public boolean doesntExist() {
        return !exists();
    }

    private boolean passThroughFilters(File file) {
        return allMatch(filters, filter -> filter.passes(file));
    }

    public void rename(String newName) {
        this.path = FileSystem.rename(path, newName);
    }
}
