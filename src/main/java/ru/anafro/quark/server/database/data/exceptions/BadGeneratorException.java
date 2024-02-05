package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.ColumnDescription;
import ru.anafro.quark.server.database.data.RecordFieldGenerator;
import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.language.entities.Entity;

public class BadGeneratorException extends DatabaseException {
    public BadGeneratorException(RecordFieldGenerator generator, Entity entity, ColumnDescription column) {
        super(STR."The generator \{generator.expression()} returned \{entity.getExactTypeName()}, which cannot be used for column \{column.name()} with type \{column.type().getName()}");
    }
}
