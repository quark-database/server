package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class DayConstructor extends EntityConstructor {
    public DayConstructor() {
        super(
                "day",
                new InstructionEntityConstructorReturnDescription("Milliseconds in 1 day", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new LongEntity(1000L * 60 * 60 * 24);
    }
}
