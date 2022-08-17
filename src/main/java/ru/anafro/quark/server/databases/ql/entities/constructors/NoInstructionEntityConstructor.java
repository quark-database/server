package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.BooleanEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructor;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

public class NoInstructionEntityConstructor extends InstructionEntityConstructor {
    public NoInstructionEntityConstructor() {
        super("no");
    }

    @Override
    protected InstructionEntity invoke(InstructionEntityConstructorArguments arguments) {
        return new BooleanEntity(false);
    }
}
