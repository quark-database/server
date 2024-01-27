package ru.anafro.quark.server.database.language.entities.constructors;

import ru.anafro.quark.server.database.language.entities.*;

public class YearConstructor extends EntityConstructor {
    public YearConstructor() {
        super(
                "year",
                new InstructionEntityConstructorReturnDescription("Milliseconds in 1 year", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new LongEntity(1000L * 60 * 60 * 24 * 365);
    }
}
