package ru.anafro.quark.server.databases.ql.exceptions;

import ru.anafro.quark.server.databases.data.ColumnDescription;
import ru.anafro.quark.server.databases.data.RecordFieldGenerator;
import ru.anafro.quark.server.databases.exceptions.QueryException;
import ru.anafro.quark.server.databases.ql.entities.Entity;

public class GeneratedValueMismatchesColumnTypeException extends QueryException {
    public GeneratedValueMismatchesColumnTypeException(RecordFieldGenerator generator, ColumnDescription description, Entity generatedEntity) {
        super("A generator '%s' generated an entity '%s' with type %s, but it's not acceptable for column '%s' with type %s.".formatted(
                generator.expression(),
                generatedEntity.toInstructionForm(),
                generatedEntity.getExactTypeName(),
                description.getName(),
                description.getType().getName()
            )
        );
    }
}
