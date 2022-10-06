package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.data.TableRecordChanger;
import ru.anafro.quark.server.databases.ql.entities.ChangerEntity;
import ru.anafro.quark.server.databases.ql.entities.EntityConstructor;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorReturnDescription.returns;

public class ChangerConstructor extends EntityConstructor {
    public ChangerConstructor() {
        super(
                "changer",

                returns("the record changer", "changer"),

                required("column name to change", "str"),
                required("changer expression", "str")
        );
    }

    @Override
    protected ChangerEntity invoke(InstructionEntityConstructorArguments arguments) {
        return new ChangerEntity(new TableRecordChanger(arguments.getString("column name to change"), arguments.getString("changer expression")));
    }
}
