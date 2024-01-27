package ru.anafro.quark.server.files;

import ru.anafro.quark.server.utils.files.Directory;

/**
 * Represents the folder of the assets of Quark.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public final class AssetDirectory extends Directory {
    private static final AssetDirectory instance = new AssetDirectory();

    public AssetDirectory() {
        super("Assets");
    }

    public static AssetDirectory getInstance() {
        return instance;
    }
}
