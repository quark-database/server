package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class MillisecondConstructor extends EntityConstructor {
    public MillisecondConstructor() {
        super(
                "millisecond",
                new InstructionEntityConstructorReturnDescription("1 millisecond", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new LongEntity(1);
    }
}
