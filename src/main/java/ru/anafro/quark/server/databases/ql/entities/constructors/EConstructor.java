package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.FloatEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructor;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

public class EConstructor extends InstructionEntityConstructor {
    public EConstructor() {
        super("e");
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.E);
    }
}
