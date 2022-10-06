package ru.anafro.quark.server.security;

/**
 * Token permission represents the permission of the
 * token. A permission is what token can actually do
 * on the server and what instructions.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class TokenPermission {

    /**
     * The permission in string.
     * @since Quark 1.1
     */
    private final String permission;

    /**
     * The all-permissions token in string.
     * @since Quark 1.1
     */
    public static final String ALL_PERMISSIONS = "*";
    public static final String ALLOWED_FOR_ALL_TOKENS = "any";

    /**
     * Creates a new token permission object.
     *
     * @param permission the permission
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public TokenPermission(String permission) {
        this.permission = permission;
    }

    /**
     * Checks whether passed permission in string can do what this token permission allows or not.
     *
     * @param permission the checking permission
     * @return {@code true} when passed permission is allowed to do what this permission allows.
     *         Otherwise, {@code false} is returned.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public boolean includesPermission(String permission) {
        if(permission.equals(ALLOWED_FOR_ALL_TOKENS)) {
            return true;
        }

        var thisPermissionEntities = this.permission.split("\\.");
        var thatPermissionEntities = permission.split("\\.");
        for(int index = 0; index < Math.min(thatPermissionEntities.length, thisPermissionEntities.length); index++) {
            if(!thisPermissionEntities[index].equals(thatPermissionEntities[index])) {
                return thatPermissionEntities[index].equals(ALL_PERMISSIONS);
            }
        }

        return true;
    }

    /**
     * @return the permission in string.
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public String getPermission() {
        return permission;
    }
}
