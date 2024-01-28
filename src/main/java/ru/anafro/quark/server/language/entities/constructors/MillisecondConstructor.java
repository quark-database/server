package ru.anafro.quark.server.language.entities.constructors;

import ru.anafro.quark.server.language.entities.*;
import ru.anafro.quark.server.utils.time.TimeSpan;

public class MillisecondConstructor extends EntityConstructor {
    public MillisecondConstructor() {
        super(
                "millisecond",
                new InstructionEntityConstructorReturnDescription("1 millisecond", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new LongEntity(TimeSpan.milliseconds(1).getMilliseconds());
    }
}
