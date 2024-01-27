package ru.anafro.quark.server.utils.files.filters;

import ru.anafro.quark.server.utils.files.File;

import static ru.anafro.quark.server.utils.arrays.Arrays.allMatch;

public class ExtensionInclusionFileFilter extends FileFilter {

    private final String[] extensions;

    public ExtensionInclusionFileFilter(String... extensions) {
        this.extensions = extensions;
    }

    public static ExtensionInclusionFileFilter withExtensions(String... extensions) {
        return new ExtensionInclusionFileFilter(extensions);
    }

    public static ExtensionInclusionFileFilter withExtension(String extension) {
        return withExtensions(extension);
    }

    @Override
    public boolean passes(File file) {
        return allMatch(extensions, file::hasExtension);
    }
}
