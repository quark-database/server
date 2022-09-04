package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class EmptyListOfConstructor extends InstructionEntityConstructor {
    public EmptyListOfConstructor() {
        super("empty list of", InstructionEntityConstructorParameter.required("type name of a new list", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new ListEntity(arguments.<StringEntity>get("type name of a new list").getString());
    }
}
