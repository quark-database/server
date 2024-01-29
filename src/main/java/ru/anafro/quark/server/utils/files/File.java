package ru.anafro.quark.server.utils.files;

import ru.anafro.quark.server.utils.files.exceptions.FileException;
import ru.anafro.quark.server.utils.strings.Strings;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;

public class File implements Comparable<File>, Appendable {
    private final Path path;
    private final java.io.File file;

    public File(Path path) {
        this.path = path;
        this.file = path.toFile();
    }

    public File(String path) {
        this(Path.of(path));
    }

    public static File create(String path) {
        var file = new File(path);
        file.create();

        return file;
    }

    public boolean exists() {
        return file.exists() && file.isFile();
    }

    public boolean doesntExist() {
        return !exists();
    }

    public String getName() {
        return file.getName();
    }

    public String getExtension() {
        var fileName = file.getName();

        return Strings.lastIndexOf(fileName, '.').map(dotIndex -> fileName.substring(dotIndex + 1)).orElse("");
    }

    public boolean hasExtension(String extension) {
        return extension.equalsIgnoreCase(this.getExtension());
    }

    public String read() {
        ensureExists();

        try {
            return Files.readString(path);
        } catch (IOException exception) {
            throw new FileException(exception.getMessage());
        }
    }

    public List<String> readLines() {
        ensureExists();

        try {
            return Files.readAllLines(path);
        } catch (IOException exception) {
            throw new FileException(exception.getMessage());
        }
    }

    public void write(String text) {
        writeWithOptions(text, StandardOpenOption.WRITE);
    }

    public void append(String text) {
        writeWithOptions(text, StandardOpenOption.APPEND);
    }

    private void writeWithOptions(String text, StandardOpenOption... options) {
        try {
            create();
            Files.writeString(path, text, options);
        } catch (IOException exception) {
            throw new FileException(exception.getMessage());
        }
    }

    public URI getURI() {
        return file.toURI();
    }

    public URL getURL() {
        try {
            return getURI().toURL();
        } catch (MalformedURLException exception) {
            throw new FileException(exception.getMessage());
        }
    }

    private void ensureExists() throws FileException {
        if (doesntExist()) {
            throw new FileException(STR."The file \{path} does not exist.");
        }
    }

    private java.io.File getIOFile() {
        return file;
    }

    public void delete() {
        @SuppressWarnings("unused") var _ = file.delete();
    }

    public void create() {
        if (exists()) {
            return;
        }

        try {
            @SuppressWarnings("unused") var _ = file.createNewFile();
        } catch (IOException exception) {
            throw new FileException(exception.getMessage());
        }
    }

    public void copy(Path destination) {
        try {
            Files.copy(path, destination);
        } catch (IOException exception) {
            throw new FileException(exception.getMessage());
        }
    }

    public Runnable makeModificationWatcherService(Runnable eventListener) {
        try {
            var watcher = FileSystems.getDefault().newWatchService();
            path.getParent().register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);

            return () -> {
                WatchKey key;

                try {
                    while (((key = watcher.take()) != null)) {
                        for (var event : key.pollEvents()) {
                            if (((Path) event.context()).toString().endsWith(file.getName())) {
                                eventListener.run();
                            }
                        }

                        if (!key.reset()) {
                            throw new FileException("Can't reset the file watcher.");
                        }
                    }
                } catch (InterruptedException exception) {
                    throw new FileException(exception.getMessage());
                }
            };
        } catch (IOException exception) {
            throw new FileException(STR."\{exception.getClass()}: \{exception.getMessage()}");
        }
    }

    @Override
    public int compareTo(File other) {
        return file.compareTo(other.getIOFile());
    }

    @Override
    public Appendable append(CharSequence sequence) throws IOException {
        Objects.requireNonNull(sequence, "Cannot append a null char sequence to a file");
        append(sequence.toString());
        return this;
    }

    @Override
    public Appendable append(CharSequence sequence, int start, int end) throws IOException {
        return append(sequence == null ? null : sequence.subSequence(start, end));
    }

    @Override
    public Appendable append(char character) throws IOException {
        append(String.valueOf(character));
        return this;
    }
}
