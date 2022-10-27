package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.data.TableRecordFinder;
import ru.anafro.quark.server.databases.ql.entities.*;

import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorReturnDescription.returns;

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
        return new FinderEntity(new TableRecordFinder(arguments.getString("column name"), arguments.get("finding object")));
    }
}
