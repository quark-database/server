package ru.anafro.quark.server.language.entities.constructors;

import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.EntityConstructor;
import ru.anafro.quark.server.language.entities.FloatEntity;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.utils.time.TimeSpan;

import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorReturnDescription.returns;

public class HoursConstructor extends EntityConstructor {

    public HoursConstructor() {
        super(
                "hours",

                returns("the amount of milliseconds", "long"),

                required("hours", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity(TimeSpan.hours(arguments.getLong("hours")).getMilliseconds());
    }
}
