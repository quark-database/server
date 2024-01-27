package ru.anafro.quark.server.database.language.entities.constructors;

import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.database.language.entities.EntityConstructor;
import ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.utils.time.TimeSpan;

import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorReturnDescription.returns;

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
        return Entity.wrap(TimeSpan.milliseconds(arguments.getLong("milliseconds")).milliseconds());
    }
}
