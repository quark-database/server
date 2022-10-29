package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class MinuteConstructor extends EntityConstructor {
    public MinuteConstructor() {
        super(
                "minute",
                new InstructionEntityConstructorReturnDescription("Milliseconds in 1 minute", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new LongEntity(1000 * 60);
    }
}
