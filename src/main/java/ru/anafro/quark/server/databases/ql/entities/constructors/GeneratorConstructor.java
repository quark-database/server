package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.data.RecordFieldGenerator;
import ru.anafro.quark.server.databases.ql.entities.*;

import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorReturnDescription.returns;

public class GeneratorConstructor extends EntityConstructor {
    public GeneratorConstructor() {
        super(
                "generator",

                returns("the field generator", "generator"),

                required("expression", "str")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new GeneratorEntity(new RecordFieldGenerator(arguments.getString("expression")));
    }
}
