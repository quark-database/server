package ru.anafro.quark.server.utils.files.filters;

import ru.anafro.quark.server.utils.files.File;

public abstract class FileFilter {
    public abstract boolean passes(File file);
}
