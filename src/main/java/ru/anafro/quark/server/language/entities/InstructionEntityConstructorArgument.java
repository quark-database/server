package ru.anafro.quark.server.language.entities;

import ru.anafro.quark.server.language.exceptions.EntityCannotBeCastedException;

import static ru.anafro.quark.server.utils.objects.Nulls.nullByDefault;

public class InstructionEntityConstructorArgument {
    private final String name;
    private final InstructionEntityConstructorArguments arguments;
    private Entity entity;
    private EntityConstructor constructor;

    public InstructionEntityConstructorArgument(String name, Entity entity) {
        this.name = name;
        this.entity = entity;
        this.constructor = null;
        this.arguments = null;
    }

    public InstructionEntityConstructorArgument(String name, EntityConstructor constructor, InstructionEntityConstructorArguments arguments) {
        this.name = name;
        this.arguments = arguments;
        this.entity = null;
        this.constructor = constructor;
    }

    public static InstructionEntityConstructorArgument computed(String name, Entity entity) {
        return new InstructionEntityConstructorArgument(name, entity);
    }

    public boolean hasType(String type) {
        return this.getEntity().hasType(type);
    }

    public String getName() {
        return name;
    }

    public boolean doesntHaveEntityComputed() {
        return entity == null;
    }

    public boolean hasConstructorToCompute() {
        return constructor != null;
    }

    public Entity getEntity() {
        if (doesntHaveEntityComputed()) {
            eval();
        }

        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void eval() {
        if (doesntHaveEntityComputed() && hasConstructorToCompute()) {
            entity = constructor.eval(arguments);
            constructor = null;
        }
    }

    public ListEntity asList() {
        var entity = getEntity();

        if (entity.doesntHaveType("list")) {
            throw new EntityCannotBeCastedException(entity, "list");
        }

        return (ListEntity) getEntity();
    }

    @Override
    public String toString() {
        var argumentValue = nullByDefault(entity, Entity::toInstructionForm);

        return STR."\{name} = \{argumentValue}";
    }
}
