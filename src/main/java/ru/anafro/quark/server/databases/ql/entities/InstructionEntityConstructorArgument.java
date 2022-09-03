package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.ql.entities.exceptions.BadInstructionEntityConstructorArgumentStateException;

public class InstructionEntityConstructorArgument {
    private final String name;
    private Entity entity;
    private InstructionEntityConstructor constructor;
    private final InstructionEntityConstructorArguments arguments;

    public InstructionEntityConstructorArgument(String name, Entity entity) {
        this.name = name;
        this.entity = entity;
        this.constructor = null;
        this.arguments = null;
    }

    public InstructionEntityConstructorArgument(String name, InstructionEntityConstructor constructor, InstructionEntityConstructorArguments arguments) {
        this.name = name;
        this.arguments = arguments;
        this.entity = null;
        this.constructor = constructor;
    }

    public static InstructionEntityConstructorArgument computed(String name, Entity entity) {
        return new InstructionEntityConstructorArgument(name, entity);
    }

    public static InstructionEntityConstructorArgument uncomputed(String name, InstructionEntityConstructor constructor, InstructionEntityConstructorArguments arguments) {
        return new InstructionEntityConstructorArgument(name, constructor, arguments);
    }

    public String getName() {
        return name;
    }

    public boolean hasEntityComputed() {
        return entity != null;
    }

    public boolean hasConstructorToCompute() {
        return constructor != null;
    }

    public Entity getEntity() {
        if(!hasEntityComputed()) {
            eval();
        }

        return entity;
    }

    public InstructionEntityConstructor getConstructor() {
        return constructor;
    }

    public void eval() {
        if(!hasEntityComputed() && hasConstructorToCompute()) {
            entity = constructor.eval(arguments);
            constructor = null;
        } else {
            throw new BadInstructionEntityConstructorArgumentStateException();
        }
    }
}
