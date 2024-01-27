package ru.anafro.quark.server.security;

import ru.anafro.quark.server.database.language.Query;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.arrays.Arrays;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import static ru.anafro.quark.server.security.TokenPermission.ALLOWED_FOR_ALL_TOKENS;

/**
 * Tokens are used for separating the access to Quark databases.
 * Some tokens can do everything, but some can do less - this
 * is made for security. This is just an interface of looking
 * up for an existing token and checking its permissions,
 * not granting them. Use direct queries instead. If token
 * does not exist in the database, all calls of {@link Token#can(String)}
 * will return {@code false}.
 *
 * @param token a token.
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public record Token(String token) {

    private static final String GENERATED_TOKENS_CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int GENERATED_TOKENS_LENGTH = 256;

    public static String generate() {
        var token = new TextBuffer();

        for (int index = 0; index < GENERATED_TOKENS_LENGTH; index++) {
            token.append(Arrays.<Character>pick(GENERATED_TOKENS_CHARACTERS.chars().mapToObj(c -> (char) c).toArray(Character[]::new)));
        }

        return token.extractContent();
    }

    /**
     * Returns {@code true} if this token has a permission.
     * If token does not exist or permission is not granted,
     * this method will return {@code false}.
     *
     * @param permission a checking permission.
     * @return (see the description)
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public boolean can(String permission) {
        if (permission.equals(ALLOWED_FOR_ALL_TOKENS)) {
            return true;
        }

        var result = Quark.query("""
                        select from "Quark.Tokens": selector = @selector("@equals(:token, \\"%s\\")");
                """.formatted(token));

        for (var tokenRecord : result.tableView()) {
            for (int index = 0; index < result.tableView().header().columnNames().length; index++) {
                if (result.tableView().header().columnNames()[index].equals("permission")) {
                    var existingPermission = tokenRecord.cells()[index];
                    var tokenPermission = new TokenPermission(permission);
                    if (tokenPermission.includesPermission(existingPermission)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean canNot(String permission) {
        return !can(permission);
    }

    public boolean canNot(Query query) {
        return canNot(query.instruction().getPermission());
    }
}
