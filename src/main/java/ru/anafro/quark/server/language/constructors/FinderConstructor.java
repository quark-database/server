package ru.anafro.quark.server.language.constructors;

import ru.anafro.quark.server.database.data.TableRecordFinder;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.EntityConstructor;
import ru.anafro.quark.server.language.entities.FinderEntity;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorArguments;

import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorReturnDescription.returns;

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
