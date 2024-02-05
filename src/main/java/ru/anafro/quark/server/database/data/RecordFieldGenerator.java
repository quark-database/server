package ru.anafro.quark.server.database.data;

import ru.anafro.quark.server.language.Expressions;
import ru.anafro.quark.server.language.entities.Entity;

public record RecordFieldGenerator(String expression) implements RecordLambda<Entity> {
    public static final String COLUMN_NAME_MARKER = ":";

    public static RecordFieldGenerator generator(String expression) {
        return new RecordFieldGenerator(expression);
    }

    @Override
    public Entity apply(TableRecord record) {
        String filledExpression = expression;   // TODO: Repeated code

        for (var field : record) {
            filledExpression = filledExpression.replace(COLUMN_NAME_MARKER + field.getColumnName(), field.getEntity().toInstructionForm());
        }

        return Expressions.eval(filledExpression);
    }
}
