package ru.anafro.quark.server.databases.ql.entities;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class StringEntity extends Entity {
    private final String value;

    public StringEntity(String value) {
        super("str");
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toInstructionForm() {
        return quoted(value);
    }

    @Override
    public String getExactTypeName() {
        return getType().getName();
    }

    @Override
    public String toRecordForm() {
        return quoted(value);
    }
}
