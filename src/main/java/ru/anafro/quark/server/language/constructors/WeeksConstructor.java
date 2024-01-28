package ru.anafro.quark.server.language.constructors;

import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.EntityConstructor;
import ru.anafro.quark.server.language.entities.FloatEntity;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.utils.time.TimeSpan;

import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorReturnDescription.returns;

public class WeeksConstructor extends EntityConstructor {
    public WeeksConstructor() {
        super(
                "weeks",

                returns("the amount of milliseconds", "long"),

                required("weeks", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity(TimeSpan.weeks(arguments.getLong("weeks")).getMilliseconds());
    }
}
