package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.data.exceptions.TableRecordSelectorExpressionShouldBeBooleanException;
import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.databases.ql.entities.BooleanEntity;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;

import java.util.Objects;

public final class ExpressionTableRecordSelector extends TableRecordSelector {
    public static final String COLUMN_NAME_MARKER = ":";
    public static final ExpressionTableRecordSelector SELECT_ALL = new ExpressionTableRecordSelector(new StringConstructorBuilder().name("yes").build());
    private final String expression;

    public ExpressionTableRecordSelector(String expression) {
        super("expression");
        this.expression = expression;
    }

    public boolean shouldBeSelected(TableRecord record) {
        return apply(record);
    }

    @Override
    public Boolean apply(TableRecord record) {
        String filledExpression = expression;

        for (var field : record) {
            filledExpression = filledExpression.replace(COLUMN_NAME_MARKER + field.getColumnName(), field.getValue().toInstructionForm());
        }

        var result = ConstructorEvaluator.eval(filledExpression);

        if (result.mismatchesType(Quark.types().get("boolean"))) {
            throw new TableRecordSelectorExpressionShouldBeBooleanException(expression, result.getType());
        }

        return ((BooleanEntity) result).getValue();
    }

    public String expression() {
        return expression;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ExpressionTableRecordSelector) obj;
        return Objects.equals(this.expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }

    @Override
    public String toString() {
        return "ExpressionTableRecordSelector[" +
                "expression=" + expression + ']';
    }

}
