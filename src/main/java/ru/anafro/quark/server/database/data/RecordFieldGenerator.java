package ru.anafro.quark.server.database.data;

import ru.anafro.quark.server.database.language.Expressions;
import ru.anafro.quark.server.database.language.entities.Entity;

public record RecordFieldGenerator(String expression) implements RecordLambda<Entity> {
    public static final String COLUMN_NAME_MARKER = ":";

    @Override
    public Entity apply(TableRecord record) {
        String filledExpression = expression;   // TODO: Repeated code

        for (var field : record) {
            filledExpression = filledExpression.replace(COLUMN_NAME_MARKER + field.getColumnName(), field.getEntity().toInstructionForm());
        }

        return Expressions.eval(filledExpression);
    }
}
