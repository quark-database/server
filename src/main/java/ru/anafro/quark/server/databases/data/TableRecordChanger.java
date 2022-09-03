package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.data.exceptions.TableRecordChangerTriesToChangeFieldThatDoesNotExistException;
import ru.anafro.quark.server.databases.data.exceptions.TableRecordChangerWrongTypeException;
import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;

public record TableRecordChanger(Table table, String column, String expression) {
    public static final String COLUMN_VALUE_PLACE_MARKER = "::";

    public void change(TableRecord record) {
        if(!record.hasField(column)) {
            throw new TableRecordChangerTriesToChangeFieldThatDoesNotExistException(table, record, this);
        }

        var result = ConstructorEvaluator.eval(expression.replace(COLUMN_VALUE_PLACE_MARKER, record.getField(column).getValue().toInstructionForm()));

        if(result.mismatchesType(record.getField(column).getValue().getType())) {
            throw new TableRecordChangerWrongTypeException(table, this, result);
        }

        record.getField(column).setValue(result);
    }
}