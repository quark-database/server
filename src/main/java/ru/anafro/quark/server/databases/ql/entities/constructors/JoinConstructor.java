package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.databases.ql.entities.constructors.exceptions.BadInstructionEntityConstructorArgumentTypeException;

import java.util.ArrayList;

public class JoinConstructor extends InstructionEntityConstructor {
    public JoinConstructor() {
        super("join", InstructionEntityConstructorParameter.required("first list", "list of ?"), InstructionEntityConstructorParameter.varargs("more lists", "list of ?"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        ListEntity firstList = arguments.get("first list");

        for(var listToJoinEntity : arguments.<ListEntity>get("more lists")) {
            var list = listToJoinEntity.valueAs(ArrayList.class);

            for(var element : list) {
                if(!firstList.getTypeOfValuesInside().equals(listToJoinEntity.getTypeName())) {
                    throw new BadInstructionEntityConstructorArgumentTypeException(this, "more lists");
                }

                firstList.add((Entity) element);
            }
        }

        return firstList;
    }
}
