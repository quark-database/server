package ru.anafro.quark.server.language.types;

import ru.anafro.quark.server.database.data.RecordFieldGenerator;
import ru.anafro.quark.server.language.Expressions;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.GeneratorEntity;
import ru.anafro.quark.server.language.entities.StringEntity;
import ru.anafro.quark.server.language.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.utils.types.exceptions.TypeException;

public class GeneratorType extends EntityType<GeneratorEntity> {
    public GeneratorType() {
        super("generator", RecordFieldGenerator.class, GeneratorEntity.class, CAN_BE_CASTED_FROM_ANY_TYPE);
    }

    @Override
    public GeneratorEntity makeEntity(String string) {
        var evaluatedEntity = Expressions.eval(string);

        if (evaluatedEntity.hasType(this)) {
            return (GeneratorEntity) evaluatedEntity;
        } else {
            throw new TypeException("GeneratorType.makeEntity(String) received a string, but after evaluation it has an unexpected type: %s, but %s required.".formatted(this.getName(), evaluatedEntity.getType()));
        }
    }

    @Override
    public String toInstructionForm(Entity entity) {
        if (entity.hasType(this)) {
            return new StringConstructorBuilder()
                    .name("generator")
                    .argument(new StringEntity(((GeneratorEntity) entity).getGenerator().expression()))
                    .build();
        } else {
            throw new TypeException("GeneratorType.toInstructionForm(InstructionEntity) received an entity, but it has a wrong type: %s, but %s required.".formatted(this.getName(), entity.getType()));
        }
    }

    @Override
    protected GeneratorEntity castOrNull(Entity entity) {
        return new GeneratorEntity(new RecordFieldGenerator(entity.toInstructionForm()));
    }
}
