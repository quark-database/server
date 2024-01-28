package ru.anafro.quark.server.database.language.entities.constructors;

import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.database.language.entities.EntityConstructor;
import ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.facade.Quark;

import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorReturnDescription.returns;

public class VarConstructor extends EntityConstructor {
    public VarConstructor() {
        super(
                "var",
                returns("The value of the variable", "any"),
                required("variable name", "str")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return Quark.variable(arguments.getString("variable name"));
    }
}
