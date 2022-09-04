package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class LowerConstructor extends InstructionEntityConstructor {
    public LowerConstructor() {
        super("lower", InstructionEntityConstructorParameter.required("string to lowercase", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new StringEntity(arguments.<StringEntity>get("string to lowercase").getValue().toLowerCase());
    }
}
