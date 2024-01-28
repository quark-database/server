package ru.anafro.quark.server.language.constructors;

import ru.anafro.quark.server.language.entities.*;
import ru.anafro.quark.server.utils.time.TimeSpan;

public class MinuteConstructor extends EntityConstructor {
    public MinuteConstructor() {
        super(
                "minute",
                new InstructionEntityConstructorReturnDescription("Milliseconds in 1 minute", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new LongEntity(TimeSpan.minutes(1).getMilliseconds());
    }
}
