package ru.anafro.quark.server.language.entities.constructors;

import ru.anafro.quark.server.language.entities.*;
import ru.anafro.quark.server.utils.time.TimeSpan;

public class WeekConstructor extends EntityConstructor {
    public WeekConstructor() {
        super(
                "week",
                new InstructionEntityConstructorReturnDescription("Milliseconds in 1 week", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new LongEntity(TimeSpan.weeks(1).getMilliseconds());
    }
}
