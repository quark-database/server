package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class SecondConstructor extends EntityConstructor {
    public SecondConstructor() {
        super(
                "second",
                new InstructionEntityConstructorReturnDescription("Milliseconds in 1 second", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new LongEntity(1000);
    }
}
