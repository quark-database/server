package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.databases.ql.entities.constructors.exceptions.BadInstructionEntityConstructorArgumentTypeException;
import ru.anafro.quark.server.utils.arrays.Arrays;

public class ToBooleanConstructor extends InstructionEntityConstructor {
    private static final String[] TRUTHY_STRING_VALUES = { "yes", "true", "+", "1", "y" };
    private static final String[] FALSY_STRING_VALUES = { "no", "false", "-", "0", "n" };

    public ToBooleanConstructor() {
        super("to boolean", InstructionEntityConstructorParameter.required("string to convert to boolean", "str"), InstructionEntityConstructorParameter.optional("default boolean value if conversation fails", "boolean"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        var string = arguments.<StringEntity>get("string to convert to boolean").getString();

        if(Arrays.contains(TRUTHY_STRING_VALUES, string)) {
            return new BooleanEntity(true);
        }

        if(Arrays.contains(FALSY_STRING_VALUES, string)) {
            return new BooleanEntity(false);
        }

        if(arguments.has("default boolean value if conversation fails")) {
            return arguments.get("default boolean value if conversation fails");
        }

        throw new BadInstructionEntityConstructorArgumentTypeException(this, "string to convert to boolean");
    }
}
