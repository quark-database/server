package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class ListConstructor extends InstructionEntityConstructor {
    public ListConstructor() {
        super("list", InstructionEntityConstructorParameter.varargs("values", "?"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        ListEntity wildcardList = arguments.get("values");

        if(wildcardList.isEmpty()) {
            return wildcardList;
        }

        ListEntity typedList = new ListEntity(wildcardList.getValue().get(0).getExactTypeName());

        for(var element : wildcardList) {
            typedList.add(element);
        }

        return typedList;
    }
}
