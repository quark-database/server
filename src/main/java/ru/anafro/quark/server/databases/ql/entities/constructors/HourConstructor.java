package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class HourConstructor extends EntityConstructor {
    public HourConstructor() {
        super(
                "hour",
                new InstructionEntityConstructorReturnDescription("Milliseconds in 1 hour", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new LongEntity(1000 * 60 * 60);
    }
}
