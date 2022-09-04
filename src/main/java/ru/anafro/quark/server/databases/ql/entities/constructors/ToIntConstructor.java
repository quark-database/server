package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.databases.ql.entities.constructors.exceptions.BadInstructionEntityConstructorArgumentTypeException;
import ru.anafro.quark.server.utils.strings.Converter;
import ru.anafro.quark.server.utils.validation.Validators;

public class ToIntConstructor extends InstructionEntityConstructor {
    public ToIntConstructor() {
        super("to int", InstructionEntityConstructorParameter.required("string to convert to integer", "str"), InstructionEntityConstructorParameter.optional("default integer value if conversation fails", "int"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        var string = arguments.<StringEntity>get("string to convert to integer").getString();

        if(Validators.validate(string, Validators.INTEGER_STRING)) {
            return new IntegerEntity(Converter.toInteger(string));
        }

        if(arguments.has("default integer value if conversation fails")) {
            return arguments.get("default integer value if conversation fails");
        }

        throw new BadInstructionEntityConstructorArgumentTypeException(this, "string to convert to integer");
    }
}
