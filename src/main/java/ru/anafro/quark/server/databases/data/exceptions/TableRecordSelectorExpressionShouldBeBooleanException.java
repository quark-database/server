package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.ql.types.EntityType;

public class TableRecordSelectorExpressionShouldBeBooleanException extends DatabaseException {
    public TableRecordSelectorExpressionShouldBeBooleanException(String expression, EntityType typeOfExpression) {
        super("An expression '%s' which has type %s is not allowed for table selector. Use boolean instead.".formatted(
                expression,
                typeOfExpression.getName()
        ));
    }
}
