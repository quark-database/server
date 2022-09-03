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
    public String getValueAsString() {
        return getValue();
    }
}
