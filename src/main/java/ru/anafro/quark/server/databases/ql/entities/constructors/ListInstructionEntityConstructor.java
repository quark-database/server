package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class ListInstructionEntityConstructor extends InstructionEntityConstructor {
    public ListInstructionEntityConstructor() {
        super("list", InstructionEntityConstructorParameter.varargs("values", "?"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        ListEntity wildcardList = arguments.get("values");

        if(wildcardList.isEmpty()) {
            return wildcardList;
        }

        ListEntity typedList = new ListEntity(wildcardList.getValue().get(0).getType());

        for(var element : wildcardList) {
            typedList.add(element);
        }

        return typedList;
    }
}
