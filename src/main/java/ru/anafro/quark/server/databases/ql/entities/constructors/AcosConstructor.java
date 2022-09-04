package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.FloatEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructor;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

public class AcosConstructor extends InstructionEntityConstructor {
    public AcosConstructor() {
        super("acos");
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.acos(arguments.<FloatEntity>get("number").getValue()));
    }
}
