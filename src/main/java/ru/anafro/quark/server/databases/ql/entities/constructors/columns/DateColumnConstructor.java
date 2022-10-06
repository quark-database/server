package ru.anafro.quark.server.databases.ql.entities.constructors.columns;

import ru.anafro.quark.server.api.Quark;

public class DateColumnConstructor extends ColumnConstructor {
    public DateColumnConstructor() {
        super("date", Quark.types().get("date"));
    }
}
