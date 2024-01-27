package ru.anafro.quark.server.security;

/**
 * Token permission represents the permission of the
 * token. A permission is what token can actually do
 * on the server and what instructions.
 *
 * @param permission The permission in string.
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public record TokenPermission(String permission) {

    /**
     * The all-permissions token in string.
     *
     * @since Quark 1.1
     */
    public static final String ALL_PERMISSIONS = "*";
    public static final String ALLOWED_FOR_ALL_TOKENS = "any";

    /**
     * Creates a new token permission object.
     *
     * @param permission the permission
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public TokenPermission {
    }

    /**
     * Checks whether passed permission in string can do what this token permission allows or not.
     *
     * @param permission the checking permission
     * @return {@code true} when passed permission is allowed to do what this permission allows.
     * Otherwise, {@code false} is returned.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public boolean includesPermission(String permission) {
        if (permission.equals(ALLOWED_FOR_ALL_TOKENS)) {
            return true;
        }

        var thisPermissionEntities = this.permission.split("\\.");
        var thatPermissionEntities = permission.split("\\.");
        for (int index = 0; index < Math.min(thatPermissionEntities.length, thisPermissionEntities.length); index++) {
            if (!thisPermissionEntities[index].equals(thatPermissionEntities[index])) {
                return thatPermissionEntities[index].equals(ALL_PERMISSIONS);
            }
        }

        return true;
    }

}
