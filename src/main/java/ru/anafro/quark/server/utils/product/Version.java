package ru.anafro.quark.server.utils.product;

import ru.anafro.quark.server.utils.strings.TextBuffer;

/**
 * Version represents the version of the software
 * releasing. Put it into the main class of your
 * application.
 *
 * @param major       The major part of the version.
 * @param minor       The minor part of the version.
 * @param patch       The patch of the version.
 * @param releaseType The release type of the version.
 * @author Anatoly Frolov
 * @version Quark 2.0
 * @since Quark 1.1
 */
public record Version(int major, int minor, int patch, String releaseType) {

    /**
     * Creates a new version object with major, minor, and patch
     * parts. Also contains the release type. Use the suggested ones:
     * <br>
     * - in development
     * - beta
     * - alpha
     * <br>
     * If the application is a release, please, use a constructor
     * without the {@code releaseType} parameter.
     *
     * @param major       The major part of the version.
     * @param minor       The minor part of the version.
     * @param patch       The patch of the version.
     * @param releaseType The release type.
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 2.0
     */
    public Version {
    }

    /**
     * Returns {@code true} if the version has a release type.
     * Otherwise, the method will return {@code false}.
     *
     * @return Does the version have the release type.
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 2.0
     */
    public boolean hasReleaseType() {
        return releaseType != null;
    }

    public String getShortVersion() {
        return STR."\{major}.\{minor}.\{patch}";
    }

    /**
     * Converts the version object to a string.
     *
     * @return the string representation of the version.
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 2.0
     */
    @Override
    public String toString() {
        var version = new TextBuffer(getShortVersion());

        if (hasReleaseType()) {
            version.append(" ", releaseType);
        }

        return version.extractContent();
    }
}
