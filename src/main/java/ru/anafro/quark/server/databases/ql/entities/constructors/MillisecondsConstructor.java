package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorReturnDescription.returns;

public class MillisecondsConstructor extends EntityConstructor {
    public MillisecondsConstructor() {
        super(
                "milliseconds",

                returns("the amount of milliseconds", "long"),

                required("milliseconds", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return arguments.<LongEntity>get("milliseconds");
    }
}
