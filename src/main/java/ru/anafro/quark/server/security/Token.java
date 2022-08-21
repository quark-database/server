package ru.anafro.quark.server.security;

/**
 * Tokens are used for separating the access to Quark databases.
 * Some tokens can do everything, but some can do less - this
 * is made for security. This is just an interface of looking
 * up for an existing token and checking its permissions,
 * not granting them. Use direct queries instead. If token
 * does not exist in the database, all calls of {@link Token#hasPermission(String)}
 * will return <code>false</code>.
 *
 * @param token a token.
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public record Token(String token) {

    /**
     * Returns <code>true</code> if this token has a permission.
     * If token does not exist or permission is not granted,
     * this method will return <code>false</code>.
     *
     * @param  permission a checking permission.
     * @return (see the description)
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public boolean hasPermission(String permission) {
        // TODO: Implement this method
        return false;
    }
}
