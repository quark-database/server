package ru.anafro.quark.server.database.data;

import ru.anafro.quark.server.database.data.exceptions.TableRecordChangerTriesToChangeFieldThatDoesNotExistException;
import ru.anafro.quark.server.database.data.exceptions.TableRecordChangerWrongTypeException;
import ru.anafro.quark.server.language.Expressions;

public record TableRecordChanger(String column, String lambda) implements RecordLambda<Void> {
    public static final String LAMBDA_FIELD_PREFIX = ":";

    public static TableRecordChanger changer(String columnName, String lambda) {
        return new TableRecordChanger(columnName, lambda);
    }

    public void change(TableRecord record) {
        apply(record);
    }

    @Override
    public Void apply(TableRecord record) {
        if (record.doesntHaveField(column)) {
            throw new TableRecordChangerTriesToChangeFieldThatDoesNotExistException(this);
        }

        var filledLambda = fillLambda(record);
        var lambdaResult = Expressions.eval(filledLambda);

        if (record.getField(column).getEntity().getType().canBeCastedFrom(lambdaResult.getType())) {
            lambdaResult = record.getField(column).getEntity().getType().cast(lambdaResult);
        }

        if (lambdaResult.doesntHaveType(record.getField(column).getEntity().getType()) || record.getField(column).getEntity().getType().canBeCastedFrom(lambdaResult.getType())) {
            throw new TableRecordChangerWrongTypeException(this, lambdaResult);
        }

        record.getField(column).set(lambdaResult);

        return null;
    }

    private String fillLambda(TableRecord record) {
        var fields = record.getFields();

        return fields.stream().map(RecordField::getColumnName).reduce(this.lambda, (lambda, columnName) -> fillLambdaField(lambda, record, columnName));
    }

    private String fillLambdaField(String lambda, TableRecord record, String columnName) {
        var lambdaField = LAMBDA_FIELD_PREFIX + columnName;
        var recordField = record.getField(columnName);
        var fieldEntity = recordField.getEntity();

        return lambda.replace(lambdaField, fieldEntity.toInstructionForm());
    }
}