package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.data.ColumnDescription;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.databases.ql.entities.exceptions.TypeCanNotBeUsedInRecordsException;

public class ColumnEntity extends Entity {
    private final ColumnDescription columnDescription;

    public ColumnEntity(ColumnDescription columnDescription) {
        super("column");
        this.columnDescription = columnDescription;
    }

    @Override
    public ColumnDescription getValue() {
        return columnDescription;
    }

    @Override
    public String getExactTypeName() {
        return getTypeName();
    }

    @Override
    public String toRecordForm() {
        throw new TypeCanNotBeUsedInRecordsException(getType());
    }

    @Override
    public String toInstructionForm() {
        return new StringConstructorBuilder()
                .name(columnDescription.getType().getName())
                .argument(new StringEntity(columnDescription.getName()))
                .build();
    }
}
