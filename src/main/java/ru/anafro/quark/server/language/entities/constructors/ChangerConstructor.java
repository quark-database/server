package ru.anafro.quark.server.language.entities.constructors;

import ru.anafro.quark.server.database.data.TableRecordChanger;
import ru.anafro.quark.server.language.entities.ChangerEntity;
import ru.anafro.quark.server.language.entities.EntityConstructor;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorArguments;

import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorReturnDescription.returns;

public class ChangerConstructor extends EntityConstructor {
    public ChangerConstructor() {
        super(
                "changer",

                returns("the record changer", "changer"),

                required("column name to change", "str"),
                required("changer lambda", "str")
        );
    }

    @Override
    protected ChangerEntity invoke(InstructionEntityConstructorArguments arguments) {
        return new ChangerEntity(new TableRecordChanger(arguments.getString("column name to change"), arguments.getString("changer lambda")));
    }
}
