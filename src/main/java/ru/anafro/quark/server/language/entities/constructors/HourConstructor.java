package ru.anafro.quark.server.language.entities.constructors;

import ru.anafro.quark.server.language.entities.*;
import ru.anafro.quark.server.utils.time.TimeSpan;

public class HourConstructor extends EntityConstructor {
    public HourConstructor() {
        super(
                "hour",
                new InstructionEntityConstructorReturnDescription("Milliseconds in 1 hour", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new LongEntity(TimeSpan.hours(1).getMilliseconds());
    }
}
