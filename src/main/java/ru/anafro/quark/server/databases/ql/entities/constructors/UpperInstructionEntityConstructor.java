package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.databases.ql.entities.constructors.exceptions.BadInstructionEntityConstructorArgumentTypeException;

public class UpperInstructionEntityConstructor extends InstructionEntityConstructor {
    public UpperInstructionEntityConstructor() {
        super("upper", InstructionEntityConstructorParameter.required("string", "str"));
    }

    @Override
    public InstructionEntity eval(InstructionEntityConstructorArguments arguments) {
        var argument = arguments.getArgument("string");

        if(argument.getEntity() instanceof StringEntity string) {
            return new StringEntity(string.toObject().toUpperCase());
        } else {
            throw new BadInstructionEntityConstructorArgumentTypeException(this, "string");
        }
    }
}
