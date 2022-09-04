package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class SortConstructor extends InstructionEntityConstructor {
    public SortConstructor() {
        super("sort", InstructionEntityConstructorParameter.required("list to sort", "list of ?"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new ListEntity(arguments.<ListEntity>get("list to sort").getTypeOfValuesInside(), arguments.<ListEntity>get("list to sort").getValue().stream().sorted().toList());
    }
}
