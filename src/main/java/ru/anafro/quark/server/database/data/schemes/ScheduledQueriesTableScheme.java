package ru.anafro.quark.server.database.data.schemes;

import static ru.anafro.quark.server.database.data.ColumnDescription.column;
import static ru.anafro.quark.server.database.data.ColumnDescription.id;
import static ru.anafro.quark.server.database.data.ColumnModifier.modifier;

public class ScheduledQueriesTableScheme extends TableScheme {
    public ScheduledQueriesTableScheme() {
        super(
                id(),
                column("query", "str", modifier("required")),
                column("period", "long", modifier("required"))
        );
    }
}
