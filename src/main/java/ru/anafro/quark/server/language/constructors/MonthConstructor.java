package ru.anafro.quark.server.language.constructors;

import ru.anafro.quark.server.language.entities.*;

public class MonthConstructor extends EntityConstructor {
    public MonthConstructor() {
        super(
                "month",
                new InstructionEntityConstructorReturnDescription("Milliseconds in 1 month", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new LongEntity(1000L * 60 * 60 * 24 * 30);
    }
}
