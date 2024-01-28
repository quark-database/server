package ru.anafro.quark.server.language.constructors;

import ru.anafro.quark.server.language.entities.*;
import ru.anafro.quark.server.utils.time.TimeSpan;

public class DayConstructor extends EntityConstructor {
    public DayConstructor() {
        super(
                "day",
                new InstructionEntityConstructorReturnDescription("Milliseconds in 1 day", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new LongEntity(TimeSpan.days(1).getMilliseconds());
    }
}
