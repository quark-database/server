package ru.anafro.quark.server.databases.ql.entities.constructors.columns;

import ru.anafro.quark.server.api.Quark;

public class BooleanColumnConstructor extends ColumnConstructor {
    public BooleanColumnConstructor() {
        super(
                "boolean",
                Quark.types().get("boolean")
        );
    }
}
