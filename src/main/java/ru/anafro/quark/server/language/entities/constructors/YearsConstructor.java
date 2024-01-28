package ru.anafro.quark.server.language.entities.constructors;

import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.EntityConstructor;
import ru.anafro.quark.server.language.entities.FloatEntity;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.utils.time.TimeSpan;

import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorReturnDescription.returns;

public class YearsConstructor extends EntityConstructor {
    public YearsConstructor() {
        super(
                "years",

                returns("the amount of milliseconds", "long"),

                required("years", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity(TimeSpan.years(arguments.getLong("years")).getMilliseconds());
    }
}
