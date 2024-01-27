package ru.anafro.quark.server.utils.files;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.utils.files.exceptions.FileException;
import ru.anafro.quark.server.utils.files.filters.FileFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static ru.anafro.quark.server.utils.arrays.Arrays.allMatch;

public class Directory implements Iterable<File> {
    private final Path path;
    private final FileFilter[] filters;

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

    public Directory getRoot() {
        return new Directory(path.getRoot());
    }

    public Path createFile(String fileName) {
        try {
            return Files.createFile(getFilePath(fileName));
        } catch (IOException exception) {
            throw new FileException(exception.getMessage());
        }
    }

    public void createFile(String fileName, String content) {
        var filePath = createFile(fileName);

        try {
            Files.writeString(filePath, content, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new FileException(exception.getMessage());
        }
    }

    public Directory createDirectory(String directoryName) {
        try {
            return new Directory(Files.createDirectories(getFilePath(directoryName)));
        } catch (IOException exception) {
            throw new FileException(exception.getMessage());
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
            throw new FileException(exception.getMessage());
        }
    }

    public Directory getSibling(String siblingName) {
        return getRoot().getDirectory(siblingName);
    }

    public void copy(Path path) {
        files().forEach(file -> file.copy(path));
    }

    public void moveTo(String directoryName) {
        try {
            Files.move(path, getRoot().getFilePath(directoryName));
        } catch (IOException exception) {
            throw new FileException(exception.getMessage());
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
            throw new FileException(exception.getMessage());
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
}
