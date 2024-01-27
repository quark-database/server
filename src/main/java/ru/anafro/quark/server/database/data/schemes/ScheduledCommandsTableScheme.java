package ru.anafro.quark.server.database.data.schemes;

import static ru.anafro.quark.server.database.data.ColumnDescription.column;
import static ru.anafro.quark.server.database.data.ColumnDescription.id;
import static ru.anafro.quark.server.database.data.ColumnModifier.modifier;

public class ScheduledCommandsTableScheme extends TableScheme {
    public ScheduledCommandsTableScheme() {
        super(
                id(),
                column("command", "str", modifier("required")),
                column("period", "long", modifier("required"))
        );
    }
}
