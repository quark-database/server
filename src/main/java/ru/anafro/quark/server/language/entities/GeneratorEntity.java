package ru.anafro.quark.server.language.entities;

import ru.anafro.quark.server.database.data.RecordFieldGenerator;
import ru.anafro.quark.server.language.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.facade.Quark;

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
    public String format() {
        return new StringConstructorBuilder().name(getTypeName()).argument(new StringEntity(generator.expression())).format();
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
