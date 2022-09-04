package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class EndsWithConstructor extends InstructionEntityConstructor {
    public EndsWithConstructor() {
        super("ends with", InstructionEntityConstructorParameter.required("string", "str"), InstructionEntityConstructorParameter.required("suffix", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new BooleanEntity(arguments.<StringEntity>get("string").getString().endsWith(arguments.<StringEntity>get("suffix").getString()));
    }
}
