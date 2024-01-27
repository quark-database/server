package ru.anafro.quark.server.database.data;

import ru.anafro.quark.server.database.data.exceptions.TableRecordSelectorExpressionShouldBeBooleanException;
import ru.anafro.quark.server.database.language.Expressions;
import ru.anafro.quark.server.database.language.entities.BooleanEntity;
import ru.anafro.quark.server.database.language.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.facade.Quark;

import java.util.Objects;

public final class ExpressionTableRecordSelector extends TableRecordSelector {
    public static final String COLUMN_NAME_MARKER = ":";
    public static final ExpressionTableRecordSelector SELECT_ALL = new ExpressionTableRecordSelector(new StringConstructorBuilder().name("yes").build());
    private final String expression;

    public ExpressionTableRecordSelector(String expression) {
        super("lambda");
        this.expression = expression;
    }

    public boolean selects(TableRecord record) {
        return apply(record);
    }

    @Override
    public Boolean apply(TableRecord record) {
        String filledExpression = expression;

        for (var field : record) {
            filledExpression = filledExpression.replace(COLUMN_NAME_MARKER + field.getColumnName(), field.getEntity().toInstructionForm());
        }

        var result = Expressions.eval(filledExpression);

        if (result.doesntHaveType(Quark.types().get("boolean"))) {
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
        return STR."ExpressionTableRecordSelector[lambda=\{expression}\{']'}";
    }

}
