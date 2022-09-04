package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.utils.integers.Integers;

public class DigitCountConstructor extends InstructionEntityConstructor {
    public DigitCountConstructor() {
        super("digit count", InstructionEntityConstructorParameter.required("integer", "int"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new IntegerEntity(Integers.countDigits(arguments.<IntegerEntity>get("integer").getValue()));
    }
}
