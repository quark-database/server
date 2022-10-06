package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.data.RecordFieldGenerator;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;

public class GeneratorEntity extends Entity {
    private RecordFieldGenerator generator;

    public GeneratorEntity(RecordFieldGenerator generator) {
        super("generator");
        this.generator = generator;
    }

    @Override
    public Object getValue() {
        return generator;
    }

    @Override
    public String getExactTypeName() {
        return getTypeName();
    }

    @Override
    public String toRecordForm() {
        return new StringConstructorBuilder()
                .name("generator")
                .argument(new StringEntity(generator.expression()))
                .build();
    }

    public RecordFieldGenerator getGenerator() {
        return generator;
    }
}
