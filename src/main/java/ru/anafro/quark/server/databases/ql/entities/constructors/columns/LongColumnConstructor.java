package ru.anafro.quark.server.databases.ql.entities.constructors.columns;

import ru.anafro.quark.server.api.Quark;

public class LongColumnConstructor extends ColumnConstructor {
    public LongColumnConstructor() {
        super("long", Quark.type("long"));
    }
}
