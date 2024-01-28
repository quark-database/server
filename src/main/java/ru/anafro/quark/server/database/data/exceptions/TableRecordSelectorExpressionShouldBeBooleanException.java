package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.language.types.EntityType;

public class TableRecordSelectorExpressionShouldBeBooleanException extends DatabaseException {
    public TableRecordSelectorExpressionShouldBeBooleanException(String expression, EntityType<?> typeOfExpression) {
        super("An lambda '%s' which has type %s is not allowed for table selector. Use boolean instead.".formatted(
                expression,
                typeOfExpression.getName()
        ));
    }
}
