package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class AndConstructor extends InstructionEntityConstructor {
    public AndConstructor() {
        super("and", InstructionEntityConstructorParameter.varargs("booleans", "boolean"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        boolean result = true;

        for(var untypedBoolean : arguments.<ListEntity>get("booleans")) {
            result = result && untypedBoolean.valueAs(Boolean.class);
        }

        return new BooleanEntity(result);
    }
}
