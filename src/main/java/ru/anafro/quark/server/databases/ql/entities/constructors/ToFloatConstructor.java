package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.databases.ql.entities.constructors.exceptions.BadInstructionEntityConstructorArgumentTypeException;
import ru.anafro.quark.server.utils.strings.Converter;
import ru.anafro.quark.server.utils.validation.Validators;

public class ToFloatConstructor extends InstructionEntityConstructor {
    public ToFloatConstructor() {
        super("to float", InstructionEntityConstructorParameter.required("string to convert to float", "str"), InstructionEntityConstructorParameter.optional("default float value if conversation fails", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        var string = arguments.<StringEntity>get("string to convert to float").getString();

        if(Validators.validate(string, Validators.FLOAT_STRING)) {
            return new FloatEntity(Converter.toFloat(string));
        }

        if(arguments.has("default float value if conversation fails")) {
            return arguments.get("default float value if conversation fails");
        }

        throw new BadInstructionEntityConstructorArgumentTypeException(this, "string to convert to float");
    }
}
