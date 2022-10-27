package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.data.RecordFieldGenerator;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;

public class GeneratorEntity extends Entity {
    private final RecordFieldGenerator generator;

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

    @Override
    public int rawCompare(Entity entity) {
        return generator.expression().compareTo(((GeneratorEntity) entity).getGenerator().expression());
    }

    @Override
    public int hashCode() {
        return Quark.stringHashingFunction().hash(generator.expression());
    }

    public RecordFieldGenerator getGenerator() {
        return generator;
    }
}
