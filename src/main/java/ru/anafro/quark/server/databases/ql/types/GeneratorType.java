package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.data.RecordFieldGenerator;
import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.GeneratorEntity;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.utils.types.exceptions.TypeException;

public class GeneratorType extends EntityType {
    public GeneratorType() {
        super("generator", CASTABLE_FROM_ANY_TYPE);
    }

    @Override
    public GeneratorEntity makeEntity(String string) {
        var evaluatedEntity = ConstructorEvaluator.eval(string);

        if(evaluatedEntity.hasType(this)) {
            return (GeneratorEntity) evaluatedEntity;
        } else {
            throw new TypeException("GeneratorType.makeEntity(String) received a string, but after evaluation it has an unexpected type: %s, but %s required.".formatted(this.getName(), evaluatedEntity.getType()));
        }
    }

    @Override
    public String toInstructionForm(Entity entity) {
        if(entity.hasType(this)) {
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
