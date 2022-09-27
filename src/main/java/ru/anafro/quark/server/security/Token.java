package ru.anafro.quark.server.security;

import ru.anafro.quark.server.api.Quark;

/**
 * Tokens are used for separating the access to Quark databases.
 * Some tokens can do everything, but some can do less - this
 * is made for security. This is just an interface of looking
 * up for an existing token and checking its permissions,
 * not granting them. Use direct queries instead. If token
 * does not exist in the database, all calls of {@link Token#hasPermission(String)}
 * will return {@code false}.
 *
 * @param token a token.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public record Token(String token) {

    /**
     * Returns {@code true} if this token has a permission.
     * If token does not exist or permission is not granted,
     * this method will return {@code false}.
     *
     * @param  permission a checking permission.
     * @return (see the description)
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public boolean hasPermission(String permission) {
        var result = Quark.runInstruction("""
                select from "Quark.Tokens": selector = @selector("@equals(:token, \\"%s\\")");
        """.formatted(token));

        for(var tokenRecord : result.tableView()) {
            for(int index = 0; index < result.tableView().header().columnNames().length; index++) {
                if(result.tableView().header().columnNames()[index].equals("permission")) {
                    var existingPermission = tokenRecord.cells()[index];
                    var tokenPermission = new TokenPermission(permission);
                    Quark.logger().error(tokenPermission.getPermission() + ", " + existingPermission + ", " + tokenPermission.includesPermission(permission));
                    if (tokenPermission.includesPermission(existingPermission)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
