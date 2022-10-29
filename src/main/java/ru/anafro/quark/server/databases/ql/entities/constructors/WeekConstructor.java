package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class WeekConstructor extends EntityConstructor {
    public WeekConstructor() {
        super(
                "week",
                new InstructionEntityConstructorReturnDescription("Milliseconds in 1 week", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new LongEntity(1000L * 60 * 60 * 24 * 7);
    }
}
