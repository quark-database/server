package ru.anafro.quark.server.utils.product;

import ru.anafro.quark.server.utils.strings.TextBuffer;

/**
 * Version represents the version of the software
 * releasing. Put it into the main class of your
 * application.
 *
 * @since   Quark 1.1
 * @version Quark 2.0
 * @author  Anatoly Frolov
 */
public class Version {
    /**
     * No release type is used to indicate that
     * the version does not have the release type.
     * Do not use it implicitly, use the constructor
     * without the release type instead.
     *
     * @since   Quark 2.0
     */
    public static final String NO_RELEASE_TYPE = null;

    /**
     * This is the string that will be between the version parts
     * when {@link Version#toString()} is invoked.
     *
     * @since   Quark 2.0
     */
    public static final String VERSION_PARTS_DELIMITER = ".";

    /**
     * The major part of the version.
     *
     * @since   Quark 1.1
     */
    private final int major;

    /**
     * The minor part of the version.
     *
     * @since   Quark 1.1
     */
    private final int minor;

    /**
     * The patch of the version.
     *
     * @since   Quark 2.0
     */
    private final int patch;

    /**
     * The release type of the version.
     *
     * @since   Quark 2.0
     */
    private final String releaseType;

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
     * @param   major       The major part of the version.
     * @param   minor       The minor part of the version.
     * @param   patch       The patch of the version.
     * @param   releaseType The release type.
     * @since   Quark 2.0
     * @author  Anatoly Frolov <contact@anafro.ru>
     */
    public Version(int major, int minor, int patch, String releaseType) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.releaseType = releaseType;
    }

    /**
     * Creates a new version object with major, minor, and patch
     * parts.
     *
     * @param   major       The major part of the version.
     * @param   minor       The minor part of the version.
     * @param   patch       The patch of the version.
     * @since   Quark 2.0
     * @author  Anatoly Frolov <contact@anafro.ru>
     */
    public Version(int major, int minor, int patch) {
        this(major, minor, patch, NO_RELEASE_TYPE);
    }

    /**
     * Returns {@code true} if the version has a release type.
     * Otherwise, the method will return {@code false}.
     *
     * @since   Quark 2.0
     * @author  Anatoly Frolov <contact@anafro.ru>
     * @return  Does the version have the release type.
     */
    public boolean hasReleaseType() {
        return releaseType != null;
    }

    /**
     * Returns the major part of the version.
     *
     * @since  Quark 2.0
     * @author Anatoly Frolov <contact@anafro.ru>
     * @return the major part of the version.
     */
    public int getMajor() {
        return major;
    }

    /**
     * Returns the minor part of the version.
     *
     * @since  Quark 2.0
     * @author Anatoly Frolov <contact@anafro.ru>
     * @return the minor part of the version.
     */
    public int getMinor() {
        return minor;
    }

    /**
     * Returns the patch of the version.
     *
     * @since  Quark 2.0
     * @author Anatoly Frolov <contact@anafro.ru>
     * @return the patch of the version.
     */
    public int getPatch() {
        return patch;
    }

    /**
     * Returns the release type of the version.
     *
     * @since  Quark 2.0
     * @author Anatoly Frolov <contact@anafro.ru>
     * @return the release type of the version.
     */
    public String getReleaseType() {
        return releaseType;
    }

    /**
     * Converts the version object to a string.
     *
     * @since   Quark 2.0
     * @author  Anatoly Frolov <contact@anafro.ru>
     * @return  the string representation of the version.
     */
    @Override
    public String toString() {
        var version = new TextBuffer();

        version.append(major, VERSION_PARTS_DELIMITER);
        version.append(minor, VERSION_PARTS_DELIMITER);
        version.append(patch);

        if(hasReleaseType()) {
            version.append(" ", releaseType);
        }

        return version.extractContent();
    }
}
