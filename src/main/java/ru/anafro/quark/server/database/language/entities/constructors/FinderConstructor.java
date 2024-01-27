package ru.anafro.quark.server.database.language.entities.constructors;

import ru.anafro.quark.server.database.data.TableRecordFinder;
import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.database.language.entities.EntityConstructor;
import ru.anafro.quark.server.database.language.entities.FinderEntity;
import ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorArguments;

import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorReturnDescription.returns;

public class FinderConstructor extends EntityConstructor {
    public FinderConstructor() {
        super(
                "finder",

                returns("the finder", "finder"),

                required("column name", "str"),
                required("finding object", "?")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FinderEntity(new TableRecordFinder(arguments.getString("column name"), arguments.getObject("finding object")));
    }
}
