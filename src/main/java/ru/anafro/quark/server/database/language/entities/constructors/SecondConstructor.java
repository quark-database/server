package ru.anafro.quark.server.database.language.entities.constructors;

import ru.anafro.quark.server.database.language.entities.*;
import ru.anafro.quark.server.utils.time.TimeSpan;

public class SecondConstructor extends EntityConstructor {
    public SecondConstructor() {
        super(
                "second",
                new InstructionEntityConstructorReturnDescription("Milliseconds in 1 second", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new LongEntity(TimeSpan.seconds(1).getMilliseconds());
    }
}
