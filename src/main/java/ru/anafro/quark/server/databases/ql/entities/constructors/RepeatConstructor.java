package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class RepeatConstructor extends InstructionEntityConstructor {
    public RepeatConstructor() {
        super("repeat", InstructionEntityConstructorParameter.required("string to repeat", "str"), InstructionEntityConstructorParameter.required("times to repeat", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        StringEntity stringToRepeat = arguments.get("string to repeat");
        IntegerEntity timesToRepeat = arguments.get("times to repeat");

        return new StringEntity(stringToRepeat.getString().repeat(timesToRepeat.getValue()));
    }
}
