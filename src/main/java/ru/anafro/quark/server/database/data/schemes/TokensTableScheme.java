package ru.anafro.quark.server.database.data.schemes;

import static ru.anafro.quark.server.database.data.ColumnDescription.column;
import static ru.anafro.quark.server.database.data.ColumnDescription.id;
import static ru.anafro.quark.server.database.data.ColumnModifier.modifier;

public class TokensTableScheme extends TableScheme {
    public TokensTableScheme() {
        super(
                id(),
                column("token", "str", modifier("required")),
                column("permission", "str", modifier("required"))
        );
    }
}
