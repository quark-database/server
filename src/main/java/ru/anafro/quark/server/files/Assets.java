package ru.anafro.quark.server.files;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;

/**
 * Represents the folder of the assets of Quark.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public final class Assets {

    /**
     * The assets folder path.
     * @since Quark 1.1
     */
    public static final String FOLDER = "Assets/";

    /**
     * This constructor must never be called. Use static methods inside
     * this class instead.
     *
     * @since Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    private Assets() {
        throw new CallingUtilityConstructorException(getClass());
    }

    /**
     * Returns a path to a file in the "assets" folder.
     *
     * @param  assetName the file in the "assets".
     * @return the file path.
     */
    public static String get(String assetName) {
        return FOLDER + assetName;
    }
}
