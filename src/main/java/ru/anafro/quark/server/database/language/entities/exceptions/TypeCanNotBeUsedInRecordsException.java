package ru.anafro.quark.server.database.language.entities.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.database.language.types.EntityType;

public class TypeCanNotBeUsedInRecordsException extends DatabaseException {
    public TypeCanNotBeUsedInRecordsException(EntityType<?> type) {
        super("An entity type %s can not be used as a field of a table record. Please, use another type or try to redesign your database if none of our datatype is suitable for you.".formatted(type.getName()));
    }
}
