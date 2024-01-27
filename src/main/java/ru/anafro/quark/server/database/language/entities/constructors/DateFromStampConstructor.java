package ru.anafro.quark.server.database.language.entities.constructors;

import ru.anafro.quark.server.database.language.entities.DateEntity;
import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.database.language.entities.EntityConstructor;
import ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorArguments;

import java.util.Date;

import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorReturnDescription.returns;

public class DateFromStampConstructor extends EntityConstructor {
    public DateFromStampConstructor() {
        super(
                "date from stamp",

                returns("the date from UNIX timestamp", "date"),

                required("unix time stamp", "long")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new DateEntity(new Date(arguments.getLong("unix time stamp")));
    }
}
