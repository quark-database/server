package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructor;
import ru.anafro.quark.server.utils.containers.UniqueList;

public class RegisteredEntityConstructors {
    private static final UniqueList<InstructionEntityConstructor> list = new UniqueList<>(
            new UpperInstructionEntityConstructor(),
            new LowerInstructionEntityConstructor(),
            new ListInstructionEntityConstructor(),
            new YesInstructionEntityConstructor(),
            new NoInstructionEntityConstructor(),
            new ConcatInstructionEntityConstructor()
    );

    public static InstructionEntityConstructor get(String constructorName) {
        for(var constructor : list) {
            if(constructor.getName().equals(constructorName)) {
                return constructor;
            }
        }

        return null;
    }

    public static boolean has(String constructorName) {
        return get(constructorName) != null;
    }
}
