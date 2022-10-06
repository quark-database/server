package ru.anafro.quark.server.databases.ql.entities.constructors.columns;

import ru.anafro.quark.server.api.Quark;

public class FloatColumnConstructor extends ColumnConstructor {
    public FloatColumnConstructor() {
        super(
                "float",
                Quark.types().get("float")
        );
    }
}
