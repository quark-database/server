package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class ToOctalStringConstructor extends InstructionEntityConstructor {
    public ToOctalStringConstructor() {
        super("to octal string", InstructionEntityConstructorParameter.required("integer to convert to octal string", "int"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new StringEntity(Integer.toOctalString(arguments.<IntegerEntity>get("integer to convert to octal string").getValue()));
    }
}