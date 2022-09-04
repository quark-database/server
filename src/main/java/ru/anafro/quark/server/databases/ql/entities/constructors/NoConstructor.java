package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.BooleanEntity;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructor;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

public class NoConstructor extends InstructionEntityConstructor {
    public NoConstructor() {
        super("no");
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new BooleanEntity(false);
    }
}
