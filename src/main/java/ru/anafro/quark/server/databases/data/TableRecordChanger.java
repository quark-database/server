package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.data.exceptions.TableRecordChangerTriesToChangeFieldThatDoesNotExistException;
import ru.anafro.quark.server.databases.data.exceptions.TableRecordChangerWrongTypeException;
import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;

public record TableRecordChanger(String column, String expression) implements RecordLambda<Void> {
    public static final String COLUMN_NAME_MARKER = ":";

    public void change(TableRecord record) {
        apply(record);
    }

    @Override
    public Void apply(TableRecord record) {
        if(!record.hasField(column)) {
            throw new TableRecordChangerTriesToChangeFieldThatDoesNotExistException(record.getTable(), record, this);
        }

        var filledExpression = expression;

        for(var field : record) {
            filledExpression = filledExpression.replace(COLUMN_NAME_MARKER + field.getColumnName(), field.getValue().toInstructionForm());
        }

        var result = ConstructorEvaluator.eval(filledExpression);

        if(record.getField(column).getValue().getType().castableFrom(result.getType())) {
            result = record.getField(column).getValue().getType().cast(result);
        }

        if(result.mismatchesType(record.getField(column).getValue().getType()) || record.getField(column).getValue().getType().castableFrom(result.getType())) {
            throw new TableRecordChangerWrongTypeException(record.getTable(), this, result);
        }

        record.getField(column).setValue(result);

        return null;
    }
}