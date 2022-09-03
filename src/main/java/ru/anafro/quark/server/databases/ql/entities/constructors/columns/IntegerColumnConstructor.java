package ru.anafro.quark.server.databases.ql.entities.constructors.columns;

import ru.anafro.quark.server.api.Quark;

public class IntegerColumnConstructor extends ColumnConstructor {
    public IntegerColumnConstructor() {
        super("int", Quark.types().get("int"));
    }
}
