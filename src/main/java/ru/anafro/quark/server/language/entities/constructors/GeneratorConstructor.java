package ru.anafro.quark.server.language.entities.constructors;

import ru.anafro.quark.server.database.data.RecordFieldGenerator;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.EntityConstructor;
import ru.anafro.quark.server.language.entities.GeneratorEntity;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorArguments;

import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorReturnDescription.returns;

public class GeneratorConstructor extends EntityConstructor {
    public GeneratorConstructor() {
        super(
                "generator",

                returns("the field generator", "generator"),

                required("lambda", "str")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new GeneratorEntity(new RecordFieldGenerator(arguments.getString("lambda")));
    }
}
