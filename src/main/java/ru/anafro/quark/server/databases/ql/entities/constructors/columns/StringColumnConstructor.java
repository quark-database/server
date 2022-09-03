package ru.anafro.quark.server.databases.ql.entities.constructors.columns;

import ru.anafro.quark.server.api.Quark;

public class StringColumnConstructor extends ColumnConstructor {
    public StringColumnConstructor() {
        super("str", Quark.types().get("str"));
    }
}
