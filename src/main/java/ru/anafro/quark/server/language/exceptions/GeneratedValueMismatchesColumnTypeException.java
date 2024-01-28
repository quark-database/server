package ru.anafro.quark.server.language.exceptions;

import ru.anafro.quark.server.database.data.ColumnDescription;
import ru.anafro.quark.server.database.data.RecordFieldGenerator;
import ru.anafro.quark.server.database.exceptions.QueryException;
import ru.anafro.quark.server.language.entities.Entity;

public class GeneratedValueMismatchesColumnTypeException extends QueryException {
    public GeneratedValueMismatchesColumnTypeException(RecordFieldGenerator generator, ColumnDescription description, Entity generatedEntity) {
        super("A generator '%s' generated an entity '%s' with type %s, but it's not acceptable for column '%s' with type %s.".formatted(
                        generator.expression(),
                        generatedEntity.toInstructionForm(),
                        generatedEntity.getExactTypeName(),
                        description.name(),
                        description.type().getName()
                )
        );
    }
}
