package ru.anafro.quark.server.databases.ql.entities.constructors.columns;

import ru.anafro.quark.server.api.Quark;

import java.util.List;

public class IdColumnConstructor extends ColumnConstructor {
    public IdColumnConstructor() {
        super(
                "id",
                Quark.types().get("int"),
                "id",
                List.of("unique", "incrementing", "positive", "constant")
        );
    }
}
