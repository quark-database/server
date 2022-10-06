package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.databases.ql.entities.Entity;

public record RecordFieldGenerator(String expression) implements RecordLambda<Entity> {
    public static final String COLUMN_NAME_MARKER = ":";

    @Override
    public Entity apply(TableRecord record) {
        String filledExpression = expression;   // TODO: Repeated code

        for(var field : record) {
            filledExpression = filledExpression.replace(COLUMN_NAME_MARKER + field.getColumnName(), field.getValue().toInstructionForm());
        }

        return ConstructorEvaluator.eval(filledExpression);
    }
}
