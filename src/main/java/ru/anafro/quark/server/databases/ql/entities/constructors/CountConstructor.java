package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class CountConstructor extends InstructionEntityConstructor {
    public CountConstructor() {
        super("count", InstructionEntityConstructorParameter.required("list to count elements", "list of ?"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new IntegerEntity(arguments.<ListEntity>get("list to count elements").size());
    }
}
