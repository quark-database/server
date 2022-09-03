package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.data.exceptions.TableRecordSelectorExpressionShouldBeBooleanException;
import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.databases.ql.entities.BooleanEntity;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;

public record TableRecordSelector(String expression) {
    public static final String COLUMN_NAME_MARKER = ":";

    public static final TableRecordSelector SELECT_ALL = new TableRecordSelector(new StringConstructorBuilder().name("yes").build());
    public boolean shouldBeSelected(TableRecord record) {
        String filledExpression = expression;

        for(var field : record) {
            filledExpression = filledExpression.replace(COLUMN_NAME_MARKER + field.getColumnName(), field.getValue().toInstructionForm());
        }

        var result = ConstructorEvaluator.eval(expression);

        if(result.mismatchesType(Quark.types().get("boolean"))) {
            throw new TableRecordSelectorExpressionShouldBeBooleanException(expression, result.getType());
        }

        return ((BooleanEntity) result).getValue();
    }
}
